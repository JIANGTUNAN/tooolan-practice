package com.tooolan.ddd.api.common.constant;

/**
 * 系统响应码常量
 * 基于 20000 系列，格式：20 + HTTP 标准状态码
 *
 * @author tooolan
 * @since 2026年2月17日
 */
public class ResponseCode {

    // ==================== 成功响应 ====================

    /**
     * 成功（对应 HTTP 200）
     */
    public static final Integer SUCCESS = 20200;

    // ==================== 客户端错误 ====================

    /**
     * 参数错误（对应 HTTP 400）
     */
    public static final Integer BAD_REQUEST = 20400;

    /**
     * 未授权/token 无效（对应 HTTP 401）
     */
    public static final Integer UNAUTHORIZED = 20401;

    /**
     * 资源不存在（对应 HTTP 404）
     */
    public static final Integer NOT_FOUND = 20404;

    /**
     * 参数校验失败（对应 HTTP 422）
     */
    public static final Integer VALIDATION_FAILED = 20422;

    // ==================== 服务器错误 ====================

    /**
     * 系统错误（对应 HTTP 500）
     */
    public static final Integer INTERNAL_ERROR = 20500;

    /**
     * 服务不可用（对应 HTTP 503）
     */
    public static final Integer SERVICE_UNAVAILABLE = 20503;

}
