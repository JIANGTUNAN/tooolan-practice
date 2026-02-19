package com.tooolan.ddd.domain.common.exception;

import com.tooolan.ddd.domain.session.constant.SessionErrorCode;

/**
 * 会话异常
 * 用于会话相关的业务异常
 *
 * @author tooolan
 * @since 2026年2月17日
 */
public class SessionException extends DomainException {

    /**
     * 使用会话错误码构造异常
     *
     * @param errorCode 会话错误码
     */
    public SessionException(SessionErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用会话错误码和自定义消息构造异常
     *
     * @param errorCode 会话错误码
     * @param message   自定义错误消息
     */
    public SessionException(SessionErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * 使用会话错误码和原因异常构造异常
     *
     * @param errorCode 会话错误码
     * @param cause     原因异常
     */
    public SessionException(SessionErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

}
