package com.tooolan.ddd.domain.common.context;

import cn.hutool.extra.servlet.JakartaServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * HTTP 请求上下文快照
 * <p>
 * 存储 HTTP 请求的元数据快照，而非 Request/Response 对象引用。
 * 在请求进入时创建，包含请求过程中可能需要的所有信息。
 *
 * @author tooolan
 * @since 2026年2月19日
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public class HttpContext {

    /**
     * 身份令牌（未登录时为 null）
     */
    private final String token;

    /**
     * 客户端 IP 地址
     */
    private final String clientIp;

    /**
     * 从 HttpServletRequest 创建上下文快照
     *
     * @param request HTTP 请求对象
     * @param token   身份令牌（可为 null）
     * @return HttpContext 快照
     */
    public static HttpContext snapshot(HttpServletRequest request, String token) {
        return HttpContext.of(
                token,
                JakartaServletUtil.getClientIP(request)
        );
    }

}
