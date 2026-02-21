package com.tooolan.ddd.api.common.web;

import cn.hutool.json.JSONUtil;
import com.tooolan.ddd.api.common.config.SecurityAuthProperties;
import com.tooolan.ddd.api.common.constant.ResponseCode;
import com.tooolan.ddd.api.common.response.ResultVo;
import com.tooolan.ddd.domain.common.context.ContextHolder;
import com.tooolan.ddd.domain.common.context.HttpContext;
import com.tooolan.ddd.domain.common.context.UserBean;
import com.tooolan.ddd.domain.session.constant.SessionErrorCode;
import com.tooolan.ddd.domain.session.service.SecurityContextProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * 上下文拦截器
 * 负责登录校验和上下文传递
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ContextInterceptor implements HandlerInterceptor {

    private final SecurityContextProvider securityContextProvider;
    private final SecurityAuthProperties authProperties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();


    /**
     * 请求预处理：登录校验和上下文初始化
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @param handler  处理器
     * @return true 放行请求，false 拦截请求
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String requestPath = request.getRequestURI();

        // 1. 创建并设置 HttpContext 快照（每个请求都有）
        String token = securityContextProvider.isLogin() ? securityContextProvider.getToken() : null;
        ContextHolder.setHttpContext(HttpContext.snapshot(request, token));

        // 2. 公开路径：只传递上下文，不校验登录
        if (isPublicPath(requestPath)) {
            setUserContextIfLoggedIn();
            return true;
        }

        // 3. 校验未启用：只传递上下文，放行所有请求
        if (!authProperties.isEnabled()) {
            setUserContextIfLoggedIn();
            return true;
        }

        // 4. 校验登录状态
        if (!securityContextProvider.isLogin()) {
            writeUnauthorizedResponse(response);
            return false;
        }

        // 5. 已登录：传递上下文
        setUserContextIfLoggedIn();
        return true;
    }

    /**
     * 请求完成后清理：清除所有上下文
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @param handler  处理器
     * @param ex       异常信息（可能为 null）
     */
    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                @NonNull Object handler, Exception ex) {
        ContextHolder.clearContext();
    }

    /**
     * 判断是否为公开路径
     *
     * @param path 请求路径
     * @return true 公开路径，false 需要登录
     */
    private boolean isPublicPath(String path) {
        return authProperties.getPublicPaths().stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    /**
     * 如果用户已登录，设置用户上下文
     */
    private void setUserContextIfLoggedIn() {
        try {
            if (securityContextProvider.isLogin()) {
                UserBean context = new UserBean(
                        securityContextProvider.getUserId(),
                        securityContextProvider.getUsername(),
                        securityContextProvider.getNickname()
                );
                ContextHolder.setContext(context);
            }
        } catch (Exception e) {
            log.warn("初始化用户上下文失败: {}", e.getMessage());
        }
    }

    /**
     * 写入未授权响应
     *
     * @param response HTTP 响应
     */
    private void writeUnauthorizedResponse(HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            ResultVo<Void> result = ResultVo.error(
                    ResponseCode.UNAUTHORIZED,
                    SessionErrorCode.NOT_LOGIN.getCode(),
                    SessionErrorCode.NOT_LOGIN.getMessage()
            );
            response.getWriter().write(JSONUtil.toJsonStr(result));
        } catch (IOException e) {
            log.error("写入未授权响应失败", e);
        }
    }

}
