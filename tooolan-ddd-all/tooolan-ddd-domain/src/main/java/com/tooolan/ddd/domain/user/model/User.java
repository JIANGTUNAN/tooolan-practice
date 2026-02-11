package com.tooolan.ddd.domain.user.model;

import com.tooolan.ddd.domain.common.AggregateRoot;
import com.tooolan.ddd.domain.common.exception.BusinessRuleException;
import com.tooolan.ddd.domain.common.identifier.TeamId;
import com.tooolan.ddd.domain.common.identifier.UserId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户 聚合根
 * 封装用户的核心业务逻辑，包括用户创建、信息更新、小组转移等行为
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends AggregateRoot<UserId> {

    /**
     * 用户名
     */
    private Username username;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private Email email;

    /**
     * 所属小组ID
     */
    private TeamId teamId;

    /**
     * 创建用户
     *
     * @param username 用户名
     * @param nickName 用户昵称
     * @param email    邮箱
     * @param teamId   所属小组ID
     * @return 用户聚合根
     */
    public static User create(Username username, String nickName, Email email, TeamId teamId) {
        User user = new User();
        user.username = username;
        user.nickName = nickName;
        user.email = email;
        user.teamId = teamId;
        return user;
    }

    /**
     * 从持久化数据恢复用户对象
     *
     * @param userId   用户ID
     * @param username 用户名
     * @param nickName 用户昵称
     * @param email    邮箱
     * @param teamId   所属小组ID
     * @param remark   备注
     * @return 用户聚合根
     */
    public static User restore(UserId userId, Username username, String nickName,
                               Email email, TeamId teamId, String remark) {
        User user = new User();
        user.id = userId;
        user.username = username;
        user.nickName = nickName;
        user.email = email;
        user.teamId = teamId;
        user.remark = remark;
        return user;
    }

    /**
     * 更新用户个人信息
     *
     * @param nickName 用户昵称
     * @param email    邮箱
     */
    public void updateProfile(String nickName, Email email) {
        this.nickName = nickName;
        this.email = email;
    }

    /**
     * 转移用户到新小组
     *
     * @param newTeamId 目标小组ID
     * @throws IllegalArgumentException 目标小组ID为空时抛出
     */
    public void transferTo(TeamId newTeamId) {
        if (newTeamId == null) {
            throw new IllegalArgumentException("目标小组ID不能为空");
        }
        this.teamId = newTeamId;
    }

    @Override
    public void validate() {
        if (username == null) {
            throw new BusinessRuleException("用户名不能为空");
        }
        if (teamId == null) {
            throw new BusinessRuleException("所属小组不能为空");
        }
    }

    /**
     * 分配ID
     * 此方法仅供基础设施层在持久化后回设ID使用
     *
     * @param userId 用户ID
     */
    public void assignId(UserId userId) {
        this.id = userId;
    }
}
