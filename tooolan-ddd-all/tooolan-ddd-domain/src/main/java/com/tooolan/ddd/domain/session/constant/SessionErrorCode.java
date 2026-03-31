package com.tooolan.ddd.domain.session.constant;

import com.tooolan.ddd.domain.common.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会话模块错误码枚举
 * 模块编码：005
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SessionErrorCode implements ErrorCode {

    /**
     * 登录失败（用户名或密码错误）
     */
    LOGIN_FAILED("1-005-401-001", "用户名或密码错误"),

    /**
     * 用户未登录
     */
    NOT_LOGIN("1-005-401-002", "用户未登录，请先登录");

    private final String code;
    private final String message;

}
