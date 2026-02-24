package com.tooolan.ddd.api.user.request;

import com.tooolan.ddd.app.user.request.ChangePasswordBo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 修改用户密码 DTO
 *
 * @author tooolan
 * @since 2026年2月24日
 */
public class ChangePasswordDTO extends ChangePasswordBo {

    @Override
    @NotNull(message = "用户ID不能为空")
    public Integer getUserId() {
        return super.getUserId();
    }

    @Override
    @NotBlank(message = "原密码不能为空")
    public String getOldPassword() {
        return super.getOldPassword();
    }

    @Override
    @NotBlank(message = "新密码不能为空")
    public String getNewPassword() {
        return super.getNewPassword();
    }

}
