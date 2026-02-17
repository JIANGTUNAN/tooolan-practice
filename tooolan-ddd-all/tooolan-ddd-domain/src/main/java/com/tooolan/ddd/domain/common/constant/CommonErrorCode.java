package com.tooolan.ddd.domain.common.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用错误码枚举
 * 包含系统级别的通用错误码
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CommonErrorCode implements ErrorCode {

    // ==================== 通用业务 ====================

    /**
     * 领域层通用错误
     */
    DOMAIN_ERROR("领域服务处理异常，请联系管理员"),

    /**
     * 业务规则错误
     */
    BUSINESS_RULE_ERROR("业务规则校验失败"),

    /**
     * 资源不存在
     */
    NOT_FOUND("请求的资源不存在"),

    // ==================== 参数校验 ====================

    /**
     * 参数校验失败
     */
    PARAM_VALIDATION_FAILED("参数校验失败"),

    /**
     * 参数约束违反
     */
    PARAM_CONSTRAINT_VIOLATION("参数约束违反"),

    // ==================== 系统 ====================

    /**
     * 非法参数
     */
    ILLEGAL_ARGUMENT("参数错误，请检查输入"),

    /**
     * 非法状态
     */
    ILLEGAL_STATE("系统繁忙，请稍后再试"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR("系统繁忙，请稍后再试"),

    /**
     * 未授权
     */
    UNAUTHORIZED("未授权，请先登录");

    private final String message;

}
