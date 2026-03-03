package com.tooolan.ddd.domain.common.exception;

import lombok.Getter;

/**
 * 领域异常基类
 * 所有业务异常都应继承此类
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Getter
public abstract class BaseException extends RuntimeException {

    /**
     * 错误码
     */
    private final String errorCode;

    /**
     * 使用错误码枚举构造领域异常
     *
     * @param errorCode 错误码枚举
     */
    protected BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
    }

    /**
     * 使用错误码枚举和原因异常构造领域异常
     *
     * @param errorCode 错误码枚举
     * @param cause     原始异常
     */
    protected BaseException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode.getCode();
    }

}
