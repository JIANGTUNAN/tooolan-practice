package com.tooolan.ddd.domain.session.exception;

import com.tooolan.ddd.domain.common.exception.BaseException;
import com.tooolan.ddd.domain.session.constant.SessionErrorCode;

/**
 * 会话异常
 * 用于会话相关的业务异常
 *
 * @author tooolan
 * @since 2026年2月17日
 */
public class SessionException extends BaseException {

    public SessionException(SessionErrorCode errorCode) {
        super(errorCode);
    }

    public SessionException(SessionErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

}
