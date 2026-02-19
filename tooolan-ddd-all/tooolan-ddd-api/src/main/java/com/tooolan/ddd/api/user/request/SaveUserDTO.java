package com.tooolan.ddd.api.user.request;

import com.tooolan.ddd.app.user.request.SaveUserBo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 保存用户 DTO
 *
 * @author tooolan
 * @since 2026年2月13日
 */
public class SaveUserDTO extends SaveUserBo {

    @Override
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 50, message = "用户名长度必须在2-50个字符之间")
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    @NotBlank(message = "用户昵称不能为空")
    @Size(max = 50, message = "用户昵称长度不能超过50个字符")
    public String getNickName() {
        return super.getNickName();
    }

    @Override
    @Email(message = "邮箱格式不正确")
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    @NotBlank(message = "密码不能为空")
    public String getPassword() {
        return super.getPassword();
    }

}
