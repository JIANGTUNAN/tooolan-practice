package com.tooolan.ddd.app.user.request;

import lombok.Data;

/**
 * 修改用户密码 BO
 *
 * @author tooolan
 * @since 2026年2月24日
 */
@Data
public class ChangePasswordBo {

    /**
     * 用户ID（必填）
     */
    private Integer userId;

    /**
     * 原始密码（RSA 加密）
     */
    private String oldPassword;

    /**
     * 新密码（RSA 加密）
     */
    private String newPassword;

}
