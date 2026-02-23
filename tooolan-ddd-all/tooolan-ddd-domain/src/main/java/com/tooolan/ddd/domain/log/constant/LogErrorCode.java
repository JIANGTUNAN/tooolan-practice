package com.tooolan.ddd.domain.log.constant;

import com.tooolan.ddd.domain.common.constant.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 日志模块错误码枚举
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
    NOT_FOUND("日志不存在"),

    /**
     * 日志保存失败
     */
    SAVE_FAILED("日志保存失败");

    private final String message;

}
