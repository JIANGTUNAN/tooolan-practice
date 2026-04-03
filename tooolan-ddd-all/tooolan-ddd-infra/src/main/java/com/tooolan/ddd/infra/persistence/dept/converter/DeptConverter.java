package com.tooolan.ddd.infra.persistence.dept.converter;

import com.tooolan.ddd.domain.dept.model.Dept;
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
     * 将数据库实体转换为领域模型
     *
     * @param entity 数据库实体
     * @return 领域模型
     */
    public static Dept toDomain(SysDeptEntity entity) {
        if (entity == null) {
            return null;
        }
        Dept dept = new Dept();
        dept.setId(entity.getDeptId());
        dept.setDeptName(entity.getDeptName());
        dept.setDeptCode(entity.getDeptCode());
        dept.setParentId(entity.getParentId());
        dept.setRemark(entity.getRemark());
        return dept;
    }

    /**
     * 将领域模型转换为数据库实体
     *
     * @param dept 领域模型
     * @return 数据库实体
     */
    public static SysDeptEntity toEntity(Dept dept) {
        if (dept == null) {
            return null;
        }
        SysDeptEntity entity = new SysDeptEntity();
        entity.setDeptId(dept.getId());
        entity.setDeptName(dept.getDeptName());
        entity.setDeptCode(dept.getDeptCode());
        entity.setParentId(dept.getParentId());
        entity.setRemark(dept.getRemark());
        return entity;
    }

}
