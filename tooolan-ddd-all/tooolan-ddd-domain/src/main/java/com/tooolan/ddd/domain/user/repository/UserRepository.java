package com.tooolan.ddd.domain.user.repository;

import com.tooolan.ddd.domain.common.param.PageQueryResult;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.repository.param.PageUserParam;

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
     * 根据用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息，不存在时返回空
     */
    Optional<User> getUser(Integer userId);

    /**
     * 分页查询用户信息
     * 支持按用户账户、昵称、邮箱、备注进行模糊查询
     * 支持按创建时间范围进行筛选
     *
     * @param pageUserParam 分页查询参数
     * @return 分页查询结果
     */
    PageQueryResult<User> pageUser(PageUserParam pageUserParam);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息，不存在时返回空
     */
    Optional<User> getUserByUsername(String username);

    /**
     * 保存用户
     * 保存成功后会将生成的 ID 回填到 user 对象中
     *
     * @param user 用户领域模型
     * @return 是否保存成功
     */
    boolean save(User user);

    /**
     * 统计指定小组的用户数量
     *
     * @param teamId 小组ID
     * @return 用户数量
     */
    long countByTeamId(Integer teamId);

    /**
     * 根据用户ID更新用户信息
     * 仅更新非 null 字段，部分更新策略
     *
     * @param user 用户领域模型
     * @return 是否更新成功
     */
    boolean updateById(User user);

    /**
     * 批量逻辑删除用户
     *
     * @param userIds 用户ID列表
     * @return 删除的记录数
     */
    int deleteByIds(List<Integer> userIds);

    /**
     * 根据用户ID列表统计有效用户数量
     *
     * @param userIds 用户ID列表
     * @return 有效用户数量
     */
    long countByIds(List<Integer> userIds);

    /**
     * 查询用户选项列表
     * 用于下拉框选择，支持按昵称模糊查询
     *
     * @param nickName 昵称（可选，模糊匹配）
     * @return 用户列表（仅包含 ID 和昵称）
     */
    List<User> listUserOptions(String nickName);

}
