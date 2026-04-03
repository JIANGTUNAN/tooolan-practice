package com.tooolan.ddd.app.dept;

import cn.hutool.core.util.ObjUtil;
import com.tooolan.ddd.app.common.response.PageVo;
import com.tooolan.ddd.app.dept.convert.DeptConvert;
import com.tooolan.ddd.app.dept.request.DeleteDeptBo;
import com.tooolan.ddd.app.dept.request.PageDeptBo;
import com.tooolan.ddd.app.dept.request.SaveDeptBo;
import com.tooolan.ddd.app.dept.request.UpdateDeptBo;
import com.tooolan.ddd.app.dept.response.DeptTreeVo;
import com.tooolan.ddd.app.dept.response.DeptVo;
import com.tooolan.ddd.domain.common.result.PageQueryResult;
import com.tooolan.ddd.domain.dept.constant.DeptErrorCode;
import com.tooolan.ddd.domain.dept.event.DeptCreatedEvent;
import com.tooolan.ddd.domain.dept.event.DeptDeletedEvent;
import com.tooolan.ddd.domain.dept.event.DeptUpdatedEvent;
import com.tooolan.ddd.domain.dept.exception.DeptException;
import com.tooolan.ddd.domain.dept.model.Dept;
import com.tooolan.ddd.domain.dept.repository.DeptRepository;
import com.tooolan.ddd.domain.dept.repository.param.PageDeptParam;
import com.tooolan.ddd.domain.dept.service.DeptDomainService;
import com.tooolan.ddd.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 部门应用服务
 * 提供部门相关的业务编排和事务管理
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Service
@RequiredArgsConstructor
public class DeptApplicationService {

    private final DeptRepository deptRepository;
    private final TeamRepository teamRepository;
    private final DeptDomainService deptDomainService;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * 根据部门ID获取部门信息
     *
     * @param deptId 部门ID
     * @return 部门视图对象
     */
    public Optional<DeptVo> getById(Integer deptId) {
        Optional<Dept> dept = deptRepository.getById(deptId);
        return dept.map(d -> {
            DeptVo vo = DeptConvert.toVo(d);
            // 填充父部门名称
            fillParentNames(Collections.singletonList(vo));
            return vo;
        });
    }

    /**
     * 分页查询部门信息
     *
     * @param bo 查询条件
     * @return 分页结果
     */
    public PageVo<DeptVo> page(PageDeptBo bo) {
        PageDeptParam param = DeptConvert.toParam(bo);
        PageQueryResult<Dept> result = deptRepository.page(param);
        if (result.getRecords().isEmpty()) {
            return PageVo.empty();
        }
        List<DeptVo> voList = DeptConvert.toVoList(result.getRecords());
        // 批量填充父部门名称
        fillParentNames(voList);
        return DeptConvert.toPageVo(result, voList);
    }

    /**
     * 查询部门树形结构
     *
     * @return 树形结构的部门列表
     */
    public List<DeptTreeVo> tree() {
        List<Dept> allDepts = deptRepository.listAll();
        return DeptTreeVo.buildTree(allDepts);
    }

    /**
     * 保存部门
     * 包含应用层校验、领域服务调用和事件发布
     *
     * @param bo 保存部门 BO
     * @throws DeptException 父部门不存在、部门编码已存在或保存失败时抛出
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void save(SaveDeptBo bo) {
        // 应用层校验：如果指定了父部门，校验父部门是否存在
        if (ObjUtil.isNotNull(bo.getParentId())) {
            if (!deptRepository.existById(bo.getParentId())) {
                throw new DeptException(DeptErrorCode.PARENT_DEPT_NOT_FOUND);
            }
        }
        // 转换为领域模型
        Dept dept = DeptConvert.toDomain(bo);
        // 调用领域服务保存部门（主键会通过引用回填）
        deptDomainService.saveDept(dept);
        // 发布部门创建事件（携带业务数据用于日志记录）
        eventPublisher.publishEvent(DeptCreatedEvent.of(dept, bo));
    }

    /**
     * 更新部门信息
     * 包含应用层校验、领域服务调用和事件发布
     *
     * @param bo 更新部门 BO
     * @throws DeptException 部门不存在、父部门不存在或不能设自身为父部门时抛出
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(UpdateDeptBo bo) {
        // 1. 查询现有部门
        Dept existingDept = deptRepository.getById(bo.getDeptId())
                .orElseThrow(() -> new DeptException(DeptErrorCode.NOT_FOUND));

        // 2. 转换为领域模型（部分更新）
        Dept updatedDept = DeptConvert.toUpdateDomain(bo);

        // 3. 如果修改了父部门，校验父部门存在性
        if (ObjUtil.notEqual(existingDept.getParentId(), bo.getParentId())) {
            if (!deptRepository.existById(bo.getParentId())) {
                throw new DeptException(DeptErrorCode.PARENT_DEPT_NOT_FOUND);
            }
        }

        // 4. 调用领域服务更新部门
        deptDomainService.updateDept(existingDept, updatedDept);

        // 5. 发布部门更新事件
        eventPublisher.publishEvent(DeptUpdatedEvent.of(updatedDept, bo));
    }

    /**
     * 批量删除部门
     * 包含应用层校验、领域服务调用和事件发布
     *
     * @param bo 删除部门 BO
     * @throws DeptException 部门不存在、有子部门或有小组时抛出
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(DeleteDeptBo bo) {
        // 应用层校验：部门是否存在
        if (!deptRepository.existByIds(bo.getDeptIds())) {
            throw new DeptException(DeptErrorCode.NOT_FOUND);
        }
        // 查询关联的小组数量
        long teamCount = teamRepository.countByDeptIds(bo.getDeptIds());
        // 调用领域服务删除部门（包含子部门和小组校验）
        deptDomainService.deleteDepts(bo.getDeptIds(), teamCount);
        // 发布部门删除事件（携带业务数据用于日志记录）
        eventPublisher.publishEvent(DeptDeletedEvent.of(bo.getDeptIds()));
    }

    /**
     * 批量填充父部门名称
     *
     * @param voList 部门视图对象列表
     */
    private void fillParentNames(List<DeptVo> voList) {
        Set<Integer> parentIds = voList.stream()
                .map(DeptVo::getParentId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (parentIds.isEmpty()) {
            return;
        }

        List<Dept> parentDepts = deptRepository.queryByIds(parentIds);
        Map<Integer, String> parentNameMap = parentDepts.stream()
                .collect(Collectors.toMap(Dept::getId, Dept::getDeptName));

        voList.forEach(vo -> {
            if (vo.getParentId() != null) {
                vo.setParentName(parentNameMap.get(vo.getParentId()));
            }
        });
    }

}
