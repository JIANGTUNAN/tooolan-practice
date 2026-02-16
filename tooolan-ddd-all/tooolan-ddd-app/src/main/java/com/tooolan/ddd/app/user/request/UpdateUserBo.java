package com.tooolan.ddd.app.user.request;

import lombok.Data;

/**
 * 更新用户 BO
 *
 * @author tooolan
 * @since 2026年2月14日
 */
@Data
public class UpdateUserBo {

    /**
     * 用户ID（必填）
     */
    private Integer userId;

    /**
     * 用户昵称（可选）
     */
    private String nickName;

    /**
     * 邮箱（可选，支持清空）
     */
    private String email;

    /**
     * 所属小组ID（可选，支持清空）
     */
    private Integer teamId;

    /**
     * 备注信息（可选，支持清空）
     */
    private String remark;

}
