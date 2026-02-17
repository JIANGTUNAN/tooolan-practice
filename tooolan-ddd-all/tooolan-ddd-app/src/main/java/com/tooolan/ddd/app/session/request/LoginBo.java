package com.tooolan.ddd.app.session.request;

import lombok.Data;

/**
 * 登录业务对象
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Data
public class LoginBo {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
