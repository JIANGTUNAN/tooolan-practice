package com.tooolan.ddd.domain.team.exception;

import com.tooolan.ddd.domain.common.exception.BaseException;
import com.tooolan.ddd.domain.team.constant.TeamErrorCode;

/**
 * 小组业务异常
 *
 * @author tooolan
 * @since 2026年3月3日
 */
public class TeamException extends BaseException {

    /**
     * 使用错误码枚举构造领域异常
     *
     * @param errorCode 错误码枚举
     */
    public TeamException(TeamErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用错误码枚举和原因异常构造领域异常
     *
     * @param errorCode 错误码枚举
     * @param cause     原始异常
     */
    public TeamException(TeamErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

}
