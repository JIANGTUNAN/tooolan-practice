package com.tooolan.ddd.domain.common.constant;

import com.tooolan.ddd.domain.common.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用错误码枚举
 * 包含系统级别的通用错误码
 * 模块编码：001
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CommonErrorCode implements ErrorCode {

    /**
     * 参数校验失败
     */
    PARAM_VALIDATION_FAILED("1-001-422-001", "参数校验失败"),

    /**
     * 参数约束违反
     */
    PARAM_CONSTRAINT_VIOLATION("1-001-422-002", "参数约束违反"),

    /**
     * 非法参数
     */
    ILLEGAL_ARGUMENT("1-001-400-001", "参数错误，请检查输入"),

    /**
     * 非法状态
     */
    ILLEGAL_STATE("1-001-500-001", "系统繁忙，请稍后再试"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR("1-001-500-002", "系统繁忙，请稍后再试"),
    ;

    private final String code;
    private final String message;

}
