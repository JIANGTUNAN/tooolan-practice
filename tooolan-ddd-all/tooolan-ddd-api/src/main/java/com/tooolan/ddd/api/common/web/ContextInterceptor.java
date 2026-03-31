package com.tooolan.ddd.api.common.web;

import cn.dev33.satoken.stp.StpUtil;
import com.tooolan.ddd.domain.common.context.ContextHolder;
import com.tooolan.ddd.domain.common.context.HttpContext;
import com.tooolan.ddd.domain.session.model.UserBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 上下文拦截器
 * 只负责上下文管理（HttpContext、UserBean），不负责鉴权
 * 鉴权由 SaTokenConfig 中的 SaInterceptor 处理
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ContextInterceptor implements HandlerInterceptor {

    /**
     * 请求预处理：上下文初始化
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @param handler  处理器
     * @return true 放行请求
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        // 1. 创建 HttpContext 快照（每个请求都有）
        String token = StpUtil.isLogin() ? StpUtil.getTokenValue() : null;
        ContextHolder.setHttpContext(HttpContext.snapshot(request, token));

        // 2. 如果已登录，初始化用户上下文
        if (StpUtil.isLogin()) {
            initUserContext();
        }

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
     * 初始化用户上下文
     * 从 Sa-Token Session 中获取用户信息
     */
    private void initUserContext() {
        try {
            UserBean userBean = new UserBean(
                    StpUtil.getLoginIdAsInt(),
                    (String) StpUtil.getSession().get(UserBean.Fields.username),
                    (String) StpUtil.getSession().get(UserBean.Fields.nickname)
            );
            ContextHolder.setContext(userBean);
        } catch (Exception e) {
            log.warn("初始化用户上下文失败: {}", e.getMessage());
        }
    }

}
