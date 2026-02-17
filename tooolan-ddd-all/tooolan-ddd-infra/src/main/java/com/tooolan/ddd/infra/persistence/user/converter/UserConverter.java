package com.tooolan.ddd.infra.persistence.user.converter;

import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.infra.persistence.user.entity.SysUserEntity;

/**
 * 用户转换器
 * 负责用户领域对象与数据库实体之间的相互转换
 *
 * @author tooolan
 * @since 2026年2月11日
 */
public class UserConverter {

    /**
     * 将数据库实体转换为领域模型
     *
     * @param entity 数据库实体
     * @return 领域模型
     */
    public static User toDomain(SysUserEntity entity) {
        if (entity == null) {
            return null;
        }
        User user = new User();
        user.setId(entity.getUserId());
        user.setUsername(entity.getUserName());
        user.setNickName(entity.getNickName());
        user.setPassword(entity.getPassword());
        user.setEmail(entity.getEmail());
        user.setTeamId(entity.getTeamId());
        user.setRemark(entity.getRemark());
        return user;
    }

    /**
     * 将领域模型转换为数据库实体
     *
     * @param user 领域模型
     * @return 数据库实体
     */
    public static SysUserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        SysUserEntity entity = new SysUserEntity();
        entity.setUserId(user.getId());
        entity.setUserName(user.getUsername());
        entity.setNickName(user.getNickName());
        entity.setPassword(user.getPassword());
        entity.setEmail(user.getEmail());
        entity.setTeamId(user.getTeamId());
        entity.setRemark(user.getRemark());
        return entity;
    }

}
