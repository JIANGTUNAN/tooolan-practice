package com.tooolan.ddd.domain.common.exception;

import com.tooolan.ddd.domain.common.constant.CommonErrorCode;
import com.tooolan.ddd.domain.common.constant.ErrorCode;

/**
 * 资源不存在异常
 * 表示请求的资源未找到
 *
 * @author tooolan
 * @since 2026年2月12日
 */
public class NotFoundException extends DomainException {

    /**
     * 使用错误码枚举构造资源不存在异常
     *
     * @param errorCode 错误码枚举
     */
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用错误码枚举和自定义消息构造资源不存在异常
     *
     * @param errorCode 错误码枚举
     * @param message   自定义错误消息
     */
    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    /**
     * 使用默认 NOT_FOUND 错误码构造资源不存在异常
     *
     * @param message 错误消息
     */
    public NotFoundException(String message) {
        super(CommonErrorCode.NOT_FOUND, message);
    }

}
