package com.tooolan.ddd.domain.common.exception;

import com.tooolan.ddd.domain.common.constant.StatusCode;

/**
 * 资源不存在异常
 * 表示请求的资源未找到
 *
 * @author tooolan
 * @since 2026年2月12日
 */
public class NotFoundException extends DomainException {

    /**
     * 构造资源不存在异常
     *
     * @param message 错误消息
     */
    public NotFoundException(String message) {
        super(StatusCode.NOT_FOUND, message);
    }

    /**
     * 构造资源不存在异常
     *
     * @param errorCode 错误码
     * @param message   错误消息
     */
    public NotFoundException(String errorCode, String message) {
        super(errorCode, message);
    }

}
