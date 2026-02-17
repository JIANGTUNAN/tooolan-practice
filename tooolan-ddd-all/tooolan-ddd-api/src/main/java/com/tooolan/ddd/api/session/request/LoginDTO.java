package com.tooolan.ddd.api.session.request;

import com.tooolan.ddd.app.session.request.LoginBo;
import jakarta.validation.constraints.NotBlank;

/**
 * 登录请求 DTO
 *
 * @author tooolan
 * @since 2026年2月17日
 */
public class LoginDTO extends LoginBo {

    @Override
    @NotBlank(message = "用户名不能为空")
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    @NotBlank(message = "密码不能为空")
    public String getPassword() {
        return super.getPassword();
    }

}
