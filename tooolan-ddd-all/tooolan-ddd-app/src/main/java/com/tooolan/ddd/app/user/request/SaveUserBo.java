package com.tooolan.ddd.app.user.request;

import lombok.Data;

/**
 * 保存用户 BO
 *
 * @author tooolan
 * @since 2026年2月13日
 */
@Data
public class SaveUserBo {

    /**
     * 用户名（必填）
     */
    private String username;

    /**
     * 用户昵称（必填）
     */
    private String nickName;

    /**
     * 邮箱（可选）
     */
    private String email;

    /**
     * 所属小组ID（可选）
     */
    private Integer teamId;

    /**
     * 备注信息（可选）
     */
    private String remark;

    /**
     * 密码（必填）
     * RSA 加密的 SHA256 摘要
     */
    private String password;

}
