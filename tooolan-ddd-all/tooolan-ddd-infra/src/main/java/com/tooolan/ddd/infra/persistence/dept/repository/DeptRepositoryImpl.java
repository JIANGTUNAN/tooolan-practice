package com.tooolan.ddd.infra.persistence.dept.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tooolan.ddd.domain.common.identifier.DeptId;
import com.tooolan.ddd.domain.dept.model.Dept;
import com.tooolan.ddd.domain.dept.model.DeptCode;
import com.tooolan.ddd.domain.dept.repository.DeptRepository;
import com.tooolan.ddd.infra.persistence.dept.converter.DeptConverter;
import com.tooolan.ddd.infra.persistence.dept.entity.SysDeptEntity;
import com.tooolan.ddd.infra.persistence.dept.mapper.SysDeptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
public class DeptRepositoryImpl implements DeptRepository {

    private final SysDeptMapper deptMapper;
    private final DeptConverter deptConverter;

    @Override
    public void save(Dept dept) {
        SysDeptEntity entity = deptConverter.toEntity(dept);

        if (dept.isNew()) {
            // 新增：插入数据
            deptMapper.insert(entity);
            // 回设ID到领域对象
            if (entity.getDeptId() != null) {
                dept.assignId(DeptId.of(entity.getDeptId()));
            }
        } else {
            // 更新：更新数据
            deptMapper.updateById(entity);
        }
    }

    @Override
    public void remove(DeptId deptId) {
        if (deptId != null) {
            deptMapper.deleteById(deptId.getValue());
        }
    }

    @Override
    public Optional<Dept> findById(DeptId deptId) {
        if (deptId == null) {
            return Optional.empty();
        }
        SysDeptEntity entity = deptMapper.selectById(deptId.getValue());
        return Optional.ofNullable(deptConverter.toDomain(entity));
    }

    @Override
    public List<Dept> findAll() {
        List<SysDeptEntity> entities = deptMapper.selectList(null);
        return entities.stream()
                .map(deptConverter::toDomain)
                .toList();
    }

    @Override
    public boolean existsByDeptCode(DeptCode deptCode) {
        if (deptCode == null) {
            return false;
        }
        LambdaQueryWrapper<SysDeptEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDeptEntity::getDeptCode, deptCode.getValue());
        return deptMapper.selectCount(wrapper) > 0;
    }

    @Override
    public Optional<Dept> findByDeptCode(DeptCode deptCode) {
        if (deptCode == null) {
            return Optional.empty();
        }
        LambdaQueryWrapper<SysDeptEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDeptEntity::getDeptCode, deptCode.getValue());
        SysDeptEntity entity = deptMapper.selectOne(wrapper);
        return Optional.ofNullable(deptConverter.toDomain(entity));
    }

    @Override
    public List<Dept> findByParentId(DeptId parentId) {
        LambdaQueryWrapper<SysDeptEntity> wrapper = new LambdaQueryWrapper<>();
        if (parentId == null) {
            wrapper.isNull(SysDeptEntity::getParentId);
        } else {
            wrapper.eq(SysDeptEntity::getParentId, parentId.getValue());
        }
        List<SysDeptEntity> entities = deptMapper.selectList(wrapper);
        return entities.stream()
                .map(deptConverter::toDomain)
                .toList();
    }

    @Override
    public List<Dept> findRootDepts() {
        LambdaQueryWrapper<SysDeptEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.isNull(SysDeptEntity::getParentId);
        List<SysDeptEntity> entities = deptMapper.selectList(wrapper);
        return entities.stream()
                .map(deptConverter::toDomain)
                .toList();
    }

    @Override
    public boolean isDeptCodeUnique(DeptCode deptCode, DeptId excludeDeptId) {
        if (deptCode == null) {
            return false;
        }
        LambdaQueryWrapper<SysDeptEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDeptEntity::getDeptCode, deptCode.getValue());
        if (excludeDeptId != null) {
            wrapper.ne(SysDeptEntity::getDeptId, excludeDeptId.getValue());
        }
        return deptMapper.selectCount(wrapper) == 0;
    }

    @Override
    public boolean hasChildren(DeptId deptId) {
        if (deptId == null) {
            return false;
        }
        LambdaQueryWrapper<SysDeptEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDeptEntity::getParentId, deptId.getValue());
        return deptMapper.selectCount(wrapper) > 0;
    }

    @Override
    public long countChildren(DeptId parentId) {
        if (parentId == null) {
            return 0;
        }
        LambdaQueryWrapper<SysDeptEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDeptEntity::getParentId, parentId.getValue());
        return deptMapper.selectCount(wrapper);
    }
}
