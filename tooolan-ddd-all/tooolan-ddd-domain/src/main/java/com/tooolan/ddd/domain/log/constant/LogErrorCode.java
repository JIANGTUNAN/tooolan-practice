package com.tooolan.ddd.domain.log.constant;

import com.tooolan.ddd.domain.common.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 日志模块错误码枚举
 * 模块编码：006
 *
 * @author tooolan
 * @since 2026年2月23日
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum LogErrorCode implements ErrorCode {

    /**
     * 日志不存在
     */
    NOT_FOUND("1-006-404-001", "日志不存在"),

    /**
     * 日志保存失败
     */
    SAVE_FAILED("1-006-500-001", "日志保存失败");

    private final String code;
    private final String message;

}
