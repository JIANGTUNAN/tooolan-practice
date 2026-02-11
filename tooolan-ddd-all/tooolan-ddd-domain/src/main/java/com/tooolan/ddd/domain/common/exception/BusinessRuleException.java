package com.tooolan.ddd.domain.common.exception;

/**
 * 业务规则异常
 * 表示业务规则验证失败，如必填字段缺失、状态不允许等
 *
 * @author tooolan
 * @since 2026年2月11日
 */
public class BusinessRuleException extends DomainException {

    /**
     * 构造业务规则异常
     *
     * @param message 错误消息
     */
    public BusinessRuleException(String message) {
        super("BUSINESS_RULE_VIOLATION", message);
    }

    /**
     * 构造业务规则异常
     *
     * @param message 错误消息
     * @param cause   原始异常
     */
    public BusinessRuleException(String message, Throwable cause) {
        super("BUSINESS_RULE_VIOLATION", message);
        this.initCause(cause);
    }
}
