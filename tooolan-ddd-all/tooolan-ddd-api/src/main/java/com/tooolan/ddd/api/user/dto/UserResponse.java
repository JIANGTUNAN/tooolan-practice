package com.tooolan.ddd.api.user.dto;

import lombok.Value;

import java.time.LocalDateTime;

/**
 * 用户响应
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Value
public class UserResponse {

    /**
     * 用户ID
     */
    Integer userId;

    /**
     * 用户名
     */
    String username;

    /**
     * 用户昵称
     */
    String nickName;

    /**
     * 邮箱
     */
    String email;

    /**
     * 所属小组ID
     */
    Integer teamId;

    /**
     * 备注
     */
    String remark;

    /**
     * 创建人
     */
    String createdBy;

    /**
     * 创建时间
     */
    LocalDateTime createdAt;

    /**
     * 更新人
     */
    String updatedBy;

    /**
     * 更新时间
     */
    LocalDateTime updatedAt;
}
