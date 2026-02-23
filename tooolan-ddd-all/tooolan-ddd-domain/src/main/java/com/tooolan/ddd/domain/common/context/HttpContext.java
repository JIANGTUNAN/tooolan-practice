package com.tooolan.ddd.domain.common.context;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpContext {

    /**
     * 请求 URI
     */
    private final String requestUri;

    /**
     * 请求方法（GET/POST/PUT/DELETE 等）
     */
    private final String method;

    /**
     * 客户端 IP 地址
     */
    private final String clientIp;

    /**
     * 身份令牌（未登录时为 null）
     */
    private final String token;

    /**
     * 请求头快照（不可变）
     */
    private final Map<String, String> headers;

    /**
     * 请求参数快照（不可变）
     */
    private final Map<String, String> params;


    /**
     * 从 HttpServletRequest 创建上下文快照
     * <p>
     * 在请求进入时调用，立即提取所有需要的数据。
     * 提取后的数据与原 Request 对象解绑，可在异步线程中安全使用。
     *
     * @param request HTTP 请求对象
     * @param token   身份令牌（可为 null）
     * @return HttpContext 快照
     */
    public static HttpContext snapshot(HttpServletRequest request, String token) {
        return new HttpContext(
                request.getRequestURI(),
                request.getMethod(),
                JakartaServletUtil.getClientIP(request),
                token,
                extractHeaders(request),
                extractParams(request)
        );
    }

    /**
     * 提取请求头为不可变 Map
     */
    private static Map<String, String> extractHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                headers.put(name, request.getHeader(name));
            }
        }
        return Collections.unmodifiableMap(headers);
    }

    /**
     * 提取请求参数为不可变 Map
     * <p>
     * 注意：多值参数只取第一个值
     */
    private static Map<String, String> extractParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (CollUtil.isNotEmpty(parameterMap)) {
            parameterMap.forEach((key, values) -> {
                if (values != null && values.length > 0) {
                    params.put(key, values[0]);
                }
            });
        }
        return Collections.unmodifiableMap(params);
    }

    /**
     * 获取指定请求头
     *
     * @param name 请求头名称
     * @return 请求头值，不存在则返回 null
     */
    public String getHeader(String name) {
        return headers.get(name);
    }

    /**
     * 获取指定请求参数
     *
     * @param name 参数名称
     * @return 参数值，不存在则返回 null
     */
    public String getParam(String name) {
        return params.get(name);
    }

}
