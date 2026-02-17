package com.tooolan.ddd.domain.common.exception;

import com.tooolan.ddd.domain.common.constant.ErrorCode;

/**
 * 业务规则异常
 * 表示业务规则验证失败
 *
 * @author tooolan
 * @since 2026年2月12日
 */
public class BusinessRuleException extends DomainException {

    /**
     * 使用错误码枚举构造业务规则异常
     *
     * @param errorCode 错误码枚举
     */
    public BusinessRuleException(ErrorCode errorCode) {
        super(errorCode);
    }

    /**
     * 使用错误码枚举和自定义消息构造业务规则异常
     *
     * @param errorCode 错误码枚举
     * @param message   自定义错误消息
     */
    public BusinessRuleException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
