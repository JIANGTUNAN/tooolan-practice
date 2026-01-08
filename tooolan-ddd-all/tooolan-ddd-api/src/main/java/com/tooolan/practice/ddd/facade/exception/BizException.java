package com.tooolan.practice.ddd.facade.exception;

import lombok.Getter;

/**
 * 业务异常类
 *
 * @author tooolan
 */
@Getter
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误描述
     */
    private final String message;

    public BizException(ErrorCode errorCode) {
        super(errorCode.getDesc());
        this.code = errorCode.getCode();
        this.message = errorCode.getDesc();
    }

    public BizException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
        this.message = message;
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BizException(String message) {
        super(message);
        this.code = ErrorCode.BIZ_ERROR.getCode();
        this.message = message;
    }

    public BizException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getDesc(), cause);
        this.code = errorCode.getCode();
        this.message = errorCode.getDesc();
    }

}
