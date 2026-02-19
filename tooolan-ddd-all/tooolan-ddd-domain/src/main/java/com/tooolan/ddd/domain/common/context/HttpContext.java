package com.tooolan.ddd.domain.common.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HTTP 请求上下文
 * 存储 HTTP 请求相关信息，每个请求都有
 *
 * @author tooolan
 * @since 2026年2月19日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpContext {

    /**
     * 身份令牌（未登录时为空）
     */
    private String token;

    /**
     * HTTP 请求对象
     */
    private HttpServletRequest request;

    /**
     * HTTP 响应对象
     */
    private HttpServletResponse response;

}
