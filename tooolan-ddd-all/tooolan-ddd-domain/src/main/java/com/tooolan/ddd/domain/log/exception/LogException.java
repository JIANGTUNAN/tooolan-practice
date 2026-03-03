package com.tooolan.ddd.domain.log.exception;

import com.tooolan.ddd.domain.common.exception.BaseException;
import com.tooolan.ddd.domain.log.constant.LogErrorCode;

/**
 * 日志业务异常
 *
 * @author tooolan
 * @since 2026年3月3日
 */
public class LogException extends BaseException {

    /**
     * 使用错误码枚举构造领域异常
     *
     * @param errorCode 错误码枚举
     */
    public LogException(LogErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用错误码枚举和原因异常构造领域异常
     *
     * @param errorCode 错误码枚举
     * @param cause     原始异常
     */
    public LogException(LogErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

}
