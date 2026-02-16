package com.tooolan.ddd.api.user.request;

import com.tooolan.ddd.app.user.request.UpdateUserBo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 更新用户 DTO
 *
 * @author tooolan
 * @since 2026年2月14日
 */
public class UpdateUserDTO extends UpdateUserBo {

    @Override
    @NotNull(message = "用户ID不能为空")
    public Integer getUserId() {
        return super.getUserId();
    }

    @Override
    @Size(max = 50, message = "用户昵称长度不能超过50个字符")
    public String getNickName() {
        return super.getNickName();
    }

    @Override
    @Email(message = "邮箱格式不正确")
    public String getEmail() {
        return super.getEmail();
    }

}
