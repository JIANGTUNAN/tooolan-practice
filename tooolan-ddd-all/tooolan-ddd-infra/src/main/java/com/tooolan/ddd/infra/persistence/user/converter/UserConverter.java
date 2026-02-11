package com.tooolan.ddd.infra.persistence.user.converter;

import com.tooolan.ddd.domain.common.identifier.TeamId;
import com.tooolan.ddd.domain.common.identifier.UserId;
import com.tooolan.ddd.domain.user.model.Email;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.model.Username;
import com.tooolan.ddd.infra.persistence.user.entity.SysUserEntity;
import org.springframework.stereotype.Component;

/**
 * 用户转换器
 * 负责用户领域对象与数据库实体之间的相互转换
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Component
public class UserConverter {

    /**
     * 实体转领域对象
     *
     * @param entity 数据库实体
     * @return 用户领域对象
     */
    public User toDomain(SysUserEntity entity) {
        if (entity == null) {
            return null;
        }

        UserId userId = entity.getUserId() != null
                ? UserId.of(entity.getUserId())
                : null;

        Username username = entity.getUserName() != null
                ? Username.of(entity.getUserName())
                : null;

        Email email = entity.getEmail() != null
                ? Email.of(entity.getEmail())
                : null;

        TeamId teamId = entity.getTeamId() != null
                ? TeamId.of(entity.getTeamId())
                : null;

        return User.restore(
                userId,
                username,
                entity.getNickName(),
                email,
                teamId,
                entity.getRemark()
        );
    }

    /**
     * 领域对象转实体
     *
     * @param user 用户领域对象
     * @return 数据库实体
     */
    public SysUserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        SysUserEntity entity = new SysUserEntity();

        // 如果领域对象有ID，则设置到实体
        if (user.getId() != null) {
            entity.setUserId(user.getId().getValue());
        }

        // 设置用户名
        if (user.getUsername() != null) {
            entity.setUserName(user.getUsername().getValue());
        }

        // 设置邮箱
        if (user.getEmail() != null) {
            entity.setEmail(user.getEmail().getValue());
        }

        // 设置小组ID
        if (user.getTeamId() != null) {
            entity.setTeamId(user.getTeamId().getValue());
        }

        entity.setNickName(user.getNickName());
        entity.setRemark(user.getRemark());

        return entity;
    }

    /**
     * 更新实体
     * 将领域对象的值更新到已有实体中
     *
     * @param entity 数据库实体
     * @param user   用户领域对象
     */
    public void updateEntity(SysUserEntity entity, User user) {
        if (entity == null || user == null) {
            return;
        }

        // 更新用户名
        if (user.getUsername() != null) {
            entity.setUserName(user.getUsername().getValue());
        }

        // 更新邮箱
        if (user.getEmail() != null) {
            entity.setEmail(user.getEmail().getValue());
        }

        // 更新小组ID
        if (user.getTeamId() != null) {
            entity.setTeamId(user.getTeamId().getValue());
        }

        entity.setNickName(user.getNickName());
        entity.setRemark(user.getRemark());
    }
}
