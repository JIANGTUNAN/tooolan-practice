package com.tooolan.ddd.infra.persistence.dept.converter;

import com.tooolan.ddd.domain.common.identifier.DeptId;
import com.tooolan.ddd.domain.dept.model.Dept;
import com.tooolan.ddd.domain.dept.model.DeptCode;
import com.tooolan.ddd.infra.persistence.dept.entity.SysDeptEntity;
import org.springframework.stereotype.Component;

/**
 * 部门转换器
 * 负责部门领域对象与数据库实体之间的相互转换
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Component
public class DeptConverter {

    /**
     * 实体转领域对象
     *
     * @param entity 数据库实体
     * @return 部门领域对象
     */
    public Dept toDomain(SysDeptEntity entity) {
        if (entity == null) {
            return null;
        }

        DeptId deptId = entity.getDeptId() != null
                ? DeptId.of(entity.getDeptId())
                : null;

        DeptId parentId = entity.getParentId() != null
                ? DeptId.of(entity.getParentId())
                : null;

        DeptCode deptCode = entity.getDeptCode() != null
                ? DeptCode.of(entity.getDeptCode())
                : null;

        return Dept.restore(
                deptId,
                entity.getDeptName(),
                deptCode,
                parentId,
                entity.getRemark()
        );
    }

    /**
     * 领域对象转实体
     *
     * @param dept 部门领域对象
     * @return 数据库实体
     */
    public SysDeptEntity toEntity(Dept dept) {
        if (dept == null) {
            return null;
        }

        SysDeptEntity entity = new SysDeptEntity();

        // 如果领域对象有ID，则设置到实体
        if (dept.getId() != null) {
            entity.setDeptId(dept.getId().getValue());
        }

        // 设置父部门ID
        if (dept.getParentId() != null) {
            entity.setParentId(dept.getParentId().getValue());
        }

        // 设置部门编码
        if (dept.getDeptCode() != null) {
            entity.setDeptCode(dept.getDeptCode().getValue());
        }

        entity.setDeptName(dept.getDeptName());
        entity.setRemark(dept.getRemark());

        return entity;
    }

    /**
     * 更新实体
     * 将领域对象的值更新到已有实体中
     *
     * @param entity 数据库实体
     * @param dept   部门领域对象
     */
    public void updateEntity(SysDeptEntity entity, Dept dept) {
        if (entity == null || dept == null) {
            return;
        }

        // 更新父部门ID
        if (dept.getParentId() != null) {
            entity.setParentId(dept.getParentId().getValue());
        } else {
            entity.setParentId(null);
        }

        // 更新部门编码
        if (dept.getDeptCode() != null) {
            entity.setDeptCode(dept.getDeptCode().getValue());
        }

        entity.setDeptName(dept.getDeptName());
        entity.setRemark(dept.getRemark());
    }
}
