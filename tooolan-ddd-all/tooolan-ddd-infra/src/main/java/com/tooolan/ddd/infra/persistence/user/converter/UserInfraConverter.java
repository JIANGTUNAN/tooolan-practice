package com.tooolan.ddd.infra.persistence.user.converter;

import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.infra.persistence.user.entity.SysUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 用户基础设施层转换器
 * 负责领域模型 ↔ 数据库实体的转换
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Mapper(componentModel = "spring")
public interface UserInfraConverter {

    /**
     * 将数据库实体转换为领域模型
     *
     * @param entity 数据库实体
     * @return 领域模型
     */
    @Mapping(source = "userId", target = "id")
    @Mapping(source = "userName", target = "username")
    User toDomain(SysUserEntity entity);

    /**
     * 将领域模型转换为数据库实体
     *
     * @param user 领域模型
     * @return 数据库实体
     */
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "id", target = "userId")
    @Mapping(source = "username", target = "userName")
    SysUserEntity toEntity(User user);

}
