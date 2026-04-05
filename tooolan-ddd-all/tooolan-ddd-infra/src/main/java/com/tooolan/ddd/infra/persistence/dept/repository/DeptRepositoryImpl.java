package com.tooolan.ddd.infra.persistence.dept.repository;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tooolan.ddd.domain.common.result.PageQueryResult;
import com.tooolan.ddd.domain.dept.model.Dept;
import com.tooolan.ddd.domain.dept.repository.DeptRepository;
import com.tooolan.ddd.domain.dept.repository.param.PageDeptParam;
import com.tooolan.ddd.infra.persistence.dept.converter.DeptInfraConverter;
import com.tooolan.ddd.infra.persistence.dept.entity.SysDeptEntity;
import com.tooolan.ddd.infra.persistence.dept.mapper.SysDeptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 部门 仓储实现
 * 实现部门领域层定义的仓储接口，提供数据持久化能力
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Repository
@RequiredArgsConstructor
public class DeptRepositoryImpl extends ServiceImpl<SysDeptMapper, SysDeptEntity> implements DeptRepository {

    private final DeptInfraConverter deptConverter;

    /**
     * 根据部门ID查询部门信息
     *
     * @param deptId 部门ID
     * @return 部门信息，不存在时返回空
     */
    @Override
    public Optional<Dept> getById(Integer deptId) {
        return super.getOptById(deptId)
                .map(deptConverter::toDomain);
    }

    /**
     * 根据部门编码查询部门信息
     *
     * @param deptCode 部门编码
     * @return 部门信息，不存在时返回空
     */
    @Override
    public Optional<Dept> getByCode(String deptCode) {
        return super.lambdaQuery()
                .eq(StrUtil.isNotBlank(deptCode), SysDeptEntity::getDeptCode, deptCode)
                .oneOpt()
                .map(deptConverter::toDomain);
    }

    /**
     * 根据部门编码判断部门是否存在
     *
     * @param deptCode 部门编码
     * @return 是否存在
     */
    @Override
    public boolean existByCode(String deptCode) {
        return super.lambdaQuery()
                .eq(StrUtil.isNotBlank(deptCode), SysDeptEntity::getDeptCode, deptCode)
                .exists();
    }

    /**
     * 分页查询部门信息
     *
     * @param param 分页查询参数
     * @return 分页查询结果
     */
    @Override
    public PageQueryResult<Dept> page(PageDeptParam param) {
        IPage<Dept> page = super.lambdaQuery()
                .like(StrUtil.isNotBlank(param.getDeptName()), SysDeptEntity::getDeptName, param.getDeptName())
                .eq(StrUtil.isNotBlank(param.getDeptCode()), SysDeptEntity::getDeptCode, param.getDeptCode())
                .eq(param.getParentId() != null, SysDeptEntity::getParentId, param.getParentId())
                .orderByDesc(SysDeptEntity::getCreatedAt)
                .page(PageDTO.of(param.getPageNum(), param.getPageSize()))
                .convert(deptConverter::toDomain);

        PageQueryResult<Dept> pageQueryResult = new PageQueryResult<>();
        BeanUtil.copyProperties(page, pageQueryResult);
        return pageQueryResult;
    }

    /**
     * 查询所有部门（用于构建树）
     *
     * @return 部门列表
     */
    @Override
    public List<Dept> listAll() {
        return super.lambdaQuery()
                .orderByDesc(SysDeptEntity::getCreatedAt)
                .list()
                .stream()
                .map(deptConverter::toDomain)
                .toList();
    }

    /**
     * 判断部门是否存在
     *
     * @param deptId 部门ID
     * @return 是否存在
     */
    @Override
    public boolean existById(Integer deptId) {
        return super.lambdaQuery()
                .eq(SysDeptEntity::getDeptId, deptId)
                .exists();
    }

    /**
     * 批量判断部门是否全部存在
     *
     * @param deptIds 部门ID列表
     * @return 所有部门都存在返回 true，否则返回 false
     */
    @Override
    public boolean existByIds(List<Integer> deptIds) {
        return super.lambdaQuery()
                .in(SysDeptEntity::getDeptId, deptIds)
                .count() == deptIds.size();
    }

    /**
     * 保存部门
     * 保存成功后会回填部门ID
     *
     * @param dept 部门领域模型
     * @return 是否保存成功
     */
    @Override
    public boolean save(Dept dept) {
        SysDeptEntity entity = deptConverter.toEntity(dept);
        boolean saved = super.save(entity);
        // 回填ID
        if (saved && entity.getDeptId() != null) {
            dept.setDeptId(entity.getDeptId());
        }
        return saved;
    }

    /**
     * 根据部门ID更新部门信息
     * 仅更新非 null 字段，部分更新策略
     *
     * @param dept 部门领域模型
     * @return 是否更新成功
     */
    @Override
    public boolean updateById(Dept dept) {
        SysDeptEntity entity = deptConverter.toEntity(dept);
        return super.updateById(entity);
    }

    /**
     * 批量逻辑删除部门
     *
     * @param deptIds 部门ID列表
     * @return 删除的记录数
     */
    @Override
    public int deleteByIds(List<Integer> deptIds) {
        return baseMapper.deleteByIds(deptIds);
    }

    /**
     * 检查是否存在子部门
     *
     * @param parentId 父部门ID
     * @return 是否存在子部门
     */
    @Override
    public boolean existsByParentId(Integer parentId) {
        return super.lambdaQuery()
                .eq(SysDeptEntity::getParentId, parentId)
                .exists();
    }

    /**
     * 根据父ID查询子部门数量
     *
     * @param parentIds 父部门ID列表
     * @return 子部门数量
     */
    @Override
    public long countByParentIds(List<Integer> parentIds) {
        if (CollUtil.isEmpty(parentIds)) {
            return 0;
        }
        return super.lambdaQuery()
                .in(SysDeptEntity::getParentId, parentIds)
                .count();
    }

    /**
     * 根据部门ID集合查询部门列表
     *
     * @param deptIds 部门ID集合
     * @return 部门列表
     */
    @Override
    public List<Dept> queryByIds(Collection<Integer> deptIds) {
        if (CollUtil.isEmpty(deptIds)) {
            return List.of();
        }
        return super.lambdaQuery()
                .in(SysDeptEntity::getDeptId, deptIds)
                .list()
                .stream()
                .map(deptConverter::toDomain)
                .toList();
    }

}
