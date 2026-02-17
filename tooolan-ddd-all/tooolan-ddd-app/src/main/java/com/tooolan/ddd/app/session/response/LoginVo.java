package com.tooolan.ddd.app.session.response;

import lombok.Data;

/**
 * 登录结果 VO
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Data
public class LoginVo {

    /**
     * 访问令牌
     */
    private String token;

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

}
