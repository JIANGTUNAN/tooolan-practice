package com.tooolan.ddd.domain.session.service;

import com.tooolan.ddd.domain.user.model.User;

/**
 * 安全上下文接口
 * 领域层通过此接口与安全框架交互，仅负责注册/注销登录状态
 * <p>
 * 注意：登录状态查询由 ContextHolder 负责，不依赖第三方框架
 *
 * @author tooolan
 * @since 2026年2月17日
 */
public interface SecurityContext {

    /**
     * 注册登录状态到安全框架
     *
     * @param user 登录用户
     * @return token
     */
    String registerLogin(User user);

    /**
     * 注销安全框架的登录状态
     */
    void unregisterLogin();

}
