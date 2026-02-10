package com.tooolan.ddd.infra.common.context;

import lombok.extern.slf4j.Slf4j;

/**
 * 请求上下文管理器
 * TODO 待实现：集成 Spring Security 或其他认证框架后，从真实会话中获取用户信息
 *
 * @author tooolan
 * @since 2026年2月10日
 */
@Slf4j
public class RequestContext {

    /**
     * 获取当前用户ID
     */
    public static String getCurrentUserId() {
        log.warn("使用测试用的上下文管理器，返回默认用户ID: system");
        return "system";
    }

}
