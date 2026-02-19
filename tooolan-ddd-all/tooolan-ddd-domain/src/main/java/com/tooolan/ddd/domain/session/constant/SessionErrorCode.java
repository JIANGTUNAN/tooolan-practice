package com.tooolan.ddd.domain.session.constant;

import com.tooolan.ddd.domain.common.constant.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会话模块错误码枚举
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
    LOGIN_FAILED("用户名或密码错误"),

    /**
     * 密码错误
     */
    PASSWORD_MISMATCH("用户名或密码错误"),

    /**
     * 用户未登录
     */
    NOT_LOGIN("用户未登录，请先登录"),

    /**
     * 密码不能为空
     */
    PASSWORD_REQUIRED("密码不能为空"),

    /**
     * 密码解密失败
     */
    PASSWORD_DECRYPT_FAILED("密码格式异常，请重新输入");

    private final String message;

}
