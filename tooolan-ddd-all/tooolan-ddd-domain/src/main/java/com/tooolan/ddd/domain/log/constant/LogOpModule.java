package com.tooolan.ddd.domain.log.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 日志操作模块常量
 * 定义日志记录中 opModule 字段的合法值
 *
 * @author tooolan
 * @since 2026年2月23日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogOpModule {

    /**
     * 会话模块
     */
    public static final String SESSION = "session";

    /**
     * 用户模块
     */
    public static final String USER = "user";

    /**
     * 小组模块
     */
    public static final String TEAM = "team";

    /**
     * 部门模块
     */
    public static final String DEPT = "dept";

}
