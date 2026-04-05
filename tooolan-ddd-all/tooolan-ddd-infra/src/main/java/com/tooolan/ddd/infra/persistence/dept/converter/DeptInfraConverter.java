package com.tooolan.ddd.infra.persistence.dept.converter;

import com.tooolan.ddd.domain.dept.model.Dept;
import com.tooolan.ddd.infra.persistence.dept.entity.SysDeptEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 部门转换器
 * 领域模型 Dept 与数据库实体 SysDeptEntity 之间的转换
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Mapper(componentModel = "spring")
public interface DeptInfraConverter {

    /**
     * 数据库实体转领域模型
     *
     * @param entity 数据库实体
     * @return 领域模型
     */
    Dept toDomain(SysDeptEntity entity);

    /**
     * 领域模型转数据库实体
     *
     * @param dept 领域模型
     * @return 数据库实体
     */
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SysDeptEntity toEntity(Dept dept);

}
