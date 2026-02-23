package com.tooolan.ddd.domain.log.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 日志操作类型常量
 * 定义日志记录中 opType 字段的合法值
 *
 * @author tooolan
 * @since 2026年2月23日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogOpType {

    /**
     * 创建操作
     */
    public static final String CREATE = "create";

    /**
     * 更新操作
     */
    public static final String UPDATE = "update";

    /**
     * 删除操作
     */
    public static final String DELETE = "delete";

    /**
     * 登录操作
     */
    public static final String LOGIN = "login";

    /**
     * 登出操作
     */
    public static final String LOGOUT = "logout";

}
