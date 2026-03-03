package com.tooolan.ddd.domain.user.exception;

import com.tooolan.ddd.domain.common.exception.BaseException;
import com.tooolan.ddd.domain.user.constant.UserErrorCode;

/**
 * 用户业务异常
 *
 * @author tooolan
 * @since 2026年3月3日
 */
public class UserException extends BaseException {

    /**
     * 使用错误码枚举构造领域异常
     *
     * @param errorCode 错误码枚举
     */
    public UserException(UserErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用错误码枚举和原因异常构造领域异常
     *
     * @param errorCode 错误码枚举
     * @param cause     原始异常
     */
    public UserException(UserErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

}
