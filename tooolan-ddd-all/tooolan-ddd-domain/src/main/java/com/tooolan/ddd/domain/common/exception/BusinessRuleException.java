package com.tooolan.ddd.domain.common.exception;

/**
 * 业务规则异常
 * 表示业务规则验证失败
 *
 * @author tooolan
 * @since 2026年2月12日
 */
public class BusinessRuleException extends DomainException {

    /**
     * 构造业务规则异常
     *
     * @param message 错误消息
     */
    public BusinessRuleException(String message) {
        super("BUSINESS_RULE_ERROR", message);
    }

    /**
     * 构造业务规则异常
     *
     * @param errorCode 错误码
     * @param message   错误消息
     */
    public BusinessRuleException(String errorCode, String message) {
        super(errorCode, message);
    }

}
