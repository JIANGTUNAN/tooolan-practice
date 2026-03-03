package com.tooolan.ddd.domain.session.service;

import com.tooolan.ddd.domain.user.model.User;

/**
 * 安全上下文提供者接口
 * 用于从安全框架获取当前登录用户信息，以及注册/注销登录状态
 *
 * @author tooolan
 * @since 2026年2月17日
 */
public interface SecurityContextProvider {

    /**
     * 判断当前请求是否已登录
     *
     * @return true 已登录，false 未登录
     */
    boolean isLogin();

    /**
     * 获取当前登录用户ID
     *
     * @return 用户ID
     */
    Integer getUserId();

    /**
     * 获取当前登录用户名
     *
     * @return 用户名
     */
    String getUsername();

    /**
     * 获取当前登录用户昵称
     *
     * @return 用户昵称
     */
    String getNickname();

    /**
     * 获取当前会话的 Token
     *
     * @return Token 值
     */
    String getToken();

    /**
     * 注册登录状态
     * 将用户信息注册到安全框架会话中
     *
     * @param user 用户信息
     * @return token
     */
    String registerLogin(User user);

    /**
     * 注销登录状态
     * 从安全框架会话中清除用户信息
     */
    void unregisterLogin();

}
