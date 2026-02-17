package com.tooolan.ddd.api.common.web;

import com.tooolan.ddd.domain.common.context.ContextHolder;
import com.tooolan.ddd.domain.common.context.UserContextBean;
import com.tooolan.ddd.domain.session.service.SecurityContextProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 上下文拦截器
 * 请求进入时初始化用户上下文，请求结束时清理
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ContextInterceptor implements HandlerInterceptor {

    private final SecurityContextProvider securityContextProvider;


    /**
     * 请求预处理：初始化用户上下文
     * 如果用户已登录，从安全框架获取用户信息并存入 ThreadLocal
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @param handler  处理器
     * @return 始终返回 true，允许请求继续
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        try {
            if (securityContextProvider.isLogin()) {
                UserContextBean context = new UserContextBean(
                        securityContextProvider.getUserId(),
                        securityContextProvider.getUsername(),
                        securityContextProvider.getNickname(),
                        securityContextProvider.getToken()
                );
                ContextHolder.setContext(context);
            }
        } catch (Exception e) {
            log.warn("初始化用户上下文失败: {}", e.getMessage());
        }
        return true;
    }

    /**
     * 请求完成后清理：清除用户上下文
     * 无论请求成功或异常，都会清理 ThreadLocal，防止内存泄漏
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

}
