package com.tooolan.ddd.domain.common.exception;

import com.tooolan.ddd.domain.common.constant.ErrorCode;
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
     * 使用错误码枚举构造领域异常
     *
     * @param errorCode 错误码枚举
     */
    public DomainException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
    }

    /**
     * 使用错误码枚举和自定义消息构造领域异常
     *
     * @param errorCode 错误码枚举
     * @param message   自定义错误消息
     */
    public DomainException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode.getCode();
    }

    /**
     * 使用错误码枚举和原因异常构造领域异常
     *
     * @param errorCode 错误码枚举
     * @param cause     原始异常
     */
    public DomainException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode.getCode();
    }

}
