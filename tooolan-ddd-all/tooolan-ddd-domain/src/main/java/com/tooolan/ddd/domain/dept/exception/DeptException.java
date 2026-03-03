package com.tooolan.ddd.domain.dept.exception;

import com.tooolan.ddd.domain.common.exception.BaseException;
import com.tooolan.ddd.domain.dept.constant.DeptErrorCode;

/**
 * 部门业务异常
 *
 * @author tooolan
 * @since 2026年3月3日
 */
public class DeptException extends BaseException {

    /**
     * 使用错误码枚举构造领域异常
     *
     * @param errorCode 错误码枚举
     */
    public DeptException(DeptErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用错误码枚举和原因异常构造领域异常
     *
     * @param errorCode 错误码枚举
     * @param cause     原始异常
     */
    public DeptException(DeptErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

}
