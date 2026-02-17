package com.tooolan.ddd.domain.common.context;

/**
 * 用户上下文接口
 * 定义获取当前登录用户信息的方法
 *
 * @author tooolan
 * @since 2026年2月17日
 */
public interface UserContext {

    /**
     * 获取用户ID
     *
     * @return 用户ID，未登录返回 null
     */
    Integer getUserId();

    /**
     * 获取用户名
     *
     * @return 用户名，未登录返回 null
     */
    String getUsername();

    /**
     * 获取用户昵称
     *
     * @return 用户昵称，未登录返回 null
     */
    String getNickname();

    /**
     * 判断是否已登录
     *
     * @return true 已登录，false 未登录
     */
    boolean isLoggedIn();

    /**
     * 获取会话ID
     *
     * @return 会话ID，未登录返回 null
     */
    String getSessionId();

}
