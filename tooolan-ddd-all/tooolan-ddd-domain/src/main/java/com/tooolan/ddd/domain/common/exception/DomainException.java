package com.tooolan.ddd.domain.common.exception;

import com.tooolan.ddd.domain.common.constant.StatusCode;
import lombok.Getter;

/**
 * 领域异常基类
 * 封装领域层抛出的所有异常，提供统一的异常处理机制
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Getter
public class DomainException extends RuntimeException {

    /**
     * 错误码
     */
    private final String errorCode;

    /**
     * 构造领域异常
     *
     * @param message 错误消息
     */
    public DomainException(String message) {
        super(message);
        this.errorCode = StatusCode.DOMAIN_ERROR;
    }

    /**
     * 构造领域异常
     *
     * @param message 错误消息
     * @param cause   原始异常
     */
    public DomainException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = StatusCode.DOMAIN_ERROR;
    }

    /**
     * 构造领域异常
     *
     * @param errorCode 错误码
     * @param message   错误消息
     */
    public DomainException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
