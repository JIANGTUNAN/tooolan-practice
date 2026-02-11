package com.tooolan.ddd.api.user.dto;

import com.tooolan.ddd.app.user.bo.UserQueryBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询请求 DTO
 * 继承 UserQueryBO，用于查询用户列表
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryRequest extends UserQueryBO {
}
