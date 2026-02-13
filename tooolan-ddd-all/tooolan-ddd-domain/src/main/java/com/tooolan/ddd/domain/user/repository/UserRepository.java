package com.tooolan.ddd.domain.user.repository;

import com.tooolan.ddd.domain.common.param.PageQueryResult;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.repository.param.PageUserParam;

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

}
