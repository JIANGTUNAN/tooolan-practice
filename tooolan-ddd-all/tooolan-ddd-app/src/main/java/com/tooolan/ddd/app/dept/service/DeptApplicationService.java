package com.tooolan.ddd.app.dept.service;

import com.tooolan.ddd.app.common.PageVo;
import com.tooolan.ddd.app.dept.bo.*;
import com.tooolan.ddd.app.dept.vo.DeptTreeNodeVo;
import com.tooolan.ddd.app.dept.vo.DeptVo;
import com.tooolan.ddd.domain.common.exception.NotFoundException;
import com.tooolan.ddd.domain.common.identifier.DeptId;
import com.tooolan.ddd.domain.common.pagination.Page;
import com.tooolan.ddd.domain.common.pagination.PageRequest;
import com.tooolan.ddd.domain.dept.model.Dept;
import com.tooolan.ddd.domain.dept.model.DeptCode;
import com.tooolan.ddd.domain.dept.repository.DeptRepository;
import com.tooolan.ddd.domain.dept.service.DeptDomainService;
import com.tooolan.ddd.domain.team.repository.TeamRepository;
import com.tooolan.ddd.app.dept.assembler.DeptAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final DeptDomainService deptDomainService;
    private final TeamRepository teamRepository;
    private final DeptAssembler deptAssembler;

    /**
     * 创建部门
     *
     * @param bo 部门创建业务对象
     * @return 部门视图对象
     */
    @Transactional(rollbackFor = Exception.class)
    public DeptVo createDept(DeptCreateBO bo) {
        // 创建部门领域对象
        Dept dept = deptAssembler.toDomain(bo);

        // 校验部门编码唯一性
        deptDomainService.validateDeptCodeUnique(dept.getDeptCode(), null);

        // 验证业务规则
        dept.validate();

        // 保存部门
        deptRepository.save(dept);

        return deptAssembler.toVo(dept);
    }

    /**
     * 更新部门信息
     *
     * @param deptId 部门ID
     * @param bo     部门更新业务对象
     * @return 部门视图对象
     */
    @Transactional(rollbackFor = Exception.class)
    public DeptVo updateDept(Integer deptId, DeptUpdateBO bo) {
        DeptId id = DeptId.of(deptId);

        // 查询部门
        Dept dept = deptRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("部门", deptId));

        // 准备更新数据
        String deptName = bo.getDeptName() != null ? bo.getDeptName() : dept.getDeptName();
        DeptCode deptCode = bo.getDeptCode() != null ? DeptCode.of(bo.getDeptCode()) : dept.getDeptCode();

        // 如果修改了编码，需要校验唯一性
        if (bo.getDeptCode() != null && !bo.getDeptCode().equals(dept.getDeptCode().getValue())) {
            deptDomainService.validateDeptCodeUnique(deptCode, id);
        }

        // 更新部门信息
        dept.updateInfo(deptName, deptCode);

        if (bo.getRemark() != null) {
            dept.setRemark(bo.getRemark());
        }

        // 验证业务规则
        dept.validate();

        // 保存部门
        deptRepository.save(dept);

        return deptAssembler.toVo(dept);
    }

    /**
     * 删除部门
     *
     * @param deptId 部门ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteDept(Integer deptId) {
        DeptId id = DeptId.of(deptId);

        // 校验部门是否可以删除
        deptDomainService.validateDeptDeletion(id);

        // 检查是否有关联小组
        long teamCount = teamRepository.countByDept(id);
        if (teamCount > 0) {
            throw new IllegalStateException("部门下还有小组，无法删除");
        }

        // 删除部门
        deptRepository.remove(id);
    }

    /**
     * 查询部门详情
     *
     * @param deptId 部门ID
     * @return 部门视图对象
     */
    public DeptVo getDeptById(Integer deptId) {
        DeptId id = DeptId.of(deptId);
        Dept dept = deptRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("部门", deptId));
        return deptAssembler.toVo(dept);
    }

    /**
     * 分页查询部门列表
     *
     * @param bo 查询业务对象
     * @return 分页视图对象
     */
    public PageVo<DeptVo> getDeptPage(DeptQueryBO bo) {
        // 构建分页请求
        PageRequest pageRequest = PageRequest.of(
                bo.getPageNum(),
                bo.getPageSize()
        );

        // 查询所有部门（简化实现，生产环境应在仓储层实现分页查询）
        List<Dept> allDepts = deptRepository.findAll();

        // 筛选条件
        List<Dept> filteredDepts = allDepts.stream()
                .filter(dept -> {
                    boolean match = true;
                    if (bo.getDeptName() != null) {
                        match = match && dept.getDeptName().contains(bo.getDeptName());
                    }
                    if (bo.getDeptCode() != null) {
                        match = match && dept.getDeptCode().getValue().contains(bo.getDeptCode());
                    }
                    if (bo.getParentId() != null) {
                        match = match && dept.getParentId() != null && dept.getParentId().getValue().equals(bo.getParentId());
                    } else if (bo.getParentId() == null) {
                        // 如果明确查询根部门
                        match = match && dept.getParentId() == null;
                    }
                    return match;
                })
                .toList();

        // 内存分页（简化实现）
        int total = filteredDepts.size();
        int start = pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), total);

        List<Dept> pagedDepts = start < total ? filteredDepts.subList(start, end) : List.of();

        Page<Dept> page = Page.of(pagedDepts, total, pageRequest.getPageNum(), pageRequest.getPageSize());

        return deptAssembler.toPageVo(page);
    }

    /**
     * 获取部门树形结构
     *
     * @return 部门树节点视图对象列表
     */
    public List<DeptTreeNodeVo> getDeptTree() {
        List<Dept> allDepts = deptRepository.findAll();
        return deptAssembler.buildTree(allDepts);
    }

    /**
     * 移动部门节点
     *
     * @param deptId 部门ID
     * @param bo     移动业务对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void moveDept(Integer deptId, DeptMoveBO bo) {
        DeptId id = DeptId.of(deptId);
        DeptId newParentId = bo.getTargetParentId() != null ? DeptId.of(bo.getTargetParentId()) : null;

        // 校验移动合法性
        deptDomainService.validateDeptMove(id, newParentId);

        // 查询部门
        Dept dept = deptRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("部门", deptId));

        // 执行移动
        dept.moveTo(newParentId);

        // 保存部门
        deptRepository.save(dept);
    }

    /**
     * 获取部门下的小组列表
     *
     * @param deptId 部门ID
     * @return 部门视图对象列表
     */
    public List<DeptVo> getDeptTeams(Integer deptId) {
        DeptId id = DeptId.of(deptId);

        // 检查部门是否存在
        if (!deptRepository.findById(id).isPresent()) {
            throw new NotFoundException("部门", deptId);
        }

        // 查询部门下的小组
        List<Dept> teams = deptRepository.findByParentId(id);

        return teams.stream()
                .map(deptAssembler::toVo)
                .toList();
    }
}
