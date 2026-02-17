package com.tooolan.ddd.domain.session.service;

/**
 * 安全上下文提供者接口
 * 用于从安全框架获取当前登录用户信息
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

}
