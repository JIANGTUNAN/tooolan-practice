package com.tooolan.ddd.app.session.response;

import lombok.Data;

/**
 * 登录状态 VO
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Data
public class LoginStatusVo {

    /**
     * 是否已登录
     */
    private boolean loggedIn;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 会话ID
     */
    private String sessionId;

}
