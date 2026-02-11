package com.tooolan.ddd.domain.user.repository;

import com.tooolan.ddd.domain.user.model.Email;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.model.Username;
import com.tooolan.ddd.domain.common.identifier.TeamId;
import com.tooolan.ddd.domain.common.identifier.UserId;

import java.util.List;
import java.util.Optional;

/**
 * 用户 仓储接口
 * 定义用户持久化操作契约，由基础设施层实现
 *
 * @author tooolan
 * @since 2026年2月11日
 */
public interface UserRepository {

    /**
     * 保存用户
     *
     * @param user 用户聚合根
     */
    void save(User user);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    void remove(UserId userId);

    /**
     * 根据ID查询用户
     *
     * @param userId 用户ID
     * @return 用户聚合根
     */
    Optional<User> findById(UserId userId);

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    List<User> findAll();

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(Username username);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户聚合根
     */
    Optional<User> findByUsername(Username username);

    /**
     * 根据小组ID查询用户列表
     *
     * @param teamId 小组ID
     * @return 用户列表
     */
    List<User> findByTeam(TeamId teamId);

    /**
     * 校验用户名唯一性（排除指定用户）
     *
     * @param username      用户名
     * @param excludeUserId 排除的用户ID
     * @return 是否唯一
     */
    boolean isUsernameUnique(Username username, UserId excludeUserId);

    /**
     * 统计小组用户数量
     *
     * @param teamId 小组ID
     * @return 用户数量
     */
    long countByTeam(TeamId teamId);
}
