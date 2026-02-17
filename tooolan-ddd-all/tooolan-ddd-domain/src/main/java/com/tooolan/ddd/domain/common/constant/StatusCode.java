package com.tooolan.ddd.domain.common.constant;

/**
 * 业务状态码常量
 * 用于标识具体的业务异常状态
 *
 * @author tooolan
 * @since 2026年2月17日
 */
public class StatusCode {

    // ==================== 通用业务状态 ====================

    /**
     * 领域层通用错误
     */
    public static final String DOMAIN_ERROR = "DOMAIN_ERROR";

    /**
     * 业务规则错误
     */
    public static final String BUSINESS_RULE_ERROR = "BUSINESS_RULE_ERROR";

    /**
     * 资源不存在
     */
    public static final String NOT_FOUND = "NOT_FOUND";

    // ==================== 用户模块状态 ====================

    /**
     * 用户名已存在
     */
    public static final String USER_USERNAME_EXISTS = "USER_USERNAME_EXISTS";

    /**
     * 用户名不可修改
     */
    public static final String USER_USERNAME_IMMUTABLE = "USER_USERNAME_IMMUTABLE";

    /**
     * 用户不存在
     */
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";

    /**
     * 保存用户失败
     */
    public static final String USER_SAVE_FAILED = "USER_SAVE_FAILED";

    /**
     * 更新用户失败
     */
    public static final String USER_UPDATE_FAILED = "USER_UPDATE_FAILED";

    // ==================== 小组模块状态 ====================

    /**
     * 小组不可用
     */
    public static final String TEAM_UNAVAILABLE = "TEAM_UNAVAILABLE";

    /**
     * 小组已满员
     */
    public static final String TEAM_FULL = "TEAM_FULL";

    /**
     * 小组不存在
     */
    public static final String TEAM_NOT_FOUND = "TEAM_NOT_FOUND";

    // ==================== 部门模块状态 ====================

    /**
     * 部门不存在
     */
    public static final String DEPT_NOT_FOUND = "DEPT_NOT_FOUND";

    /**
     * 部门编码已存在
     */
    public static final String DEPT_CODE_EXISTS = "DEPT_CODE_EXISTS";

    // ==================== 参数校验状态 ====================

    /**
     * 参数校验失败
     */
    public static final String PARAM_VALIDATION_FAILED = "PARAM_VALIDATION_FAILED";

    /**
     * 参数约束违反
     */
    public static final String PARAM_CONSTRAINT_VIOLATION = "PARAM_CONSTRAINT_VIOLATION";

    // ==================== 系统状态 ====================

    /**
     * 非法参数
     */
    public static final String ILLEGAL_ARGUMENT = "ILLEGAL_ARGUMENT";

    /**
     * 非法状态
     */
    public static final String ILLEGAL_STATE = "ILLEGAL_STATE";

    /**
     * 系统错误
     */
    public static final String SYSTEM_ERROR = "SYSTEM_ERROR";

    /**
     * 未授权
     */
    public static final String UNAUTHORIZED = "UNAUTHORIZED";

}
