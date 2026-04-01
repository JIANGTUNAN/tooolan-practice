package com.tooolan.ddd.api.common.web;

import cn.dev33.satoken.fun.SaParamFunction;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.NumberUtil;
import com.tooolan.ddd.api.common.config.SecurityAuthProperties;
import com.tooolan.ddd.api.common.constant.MockTokenConstant;
import com.tooolan.ddd.domain.common.context.ContextHolder;
import com.tooolan.ddd.domain.common.context.HttpContext;
import com.tooolan.ddd.domain.session.constant.SessionErrorCode;
import com.tooolan.ddd.domain.session.exception.SessionException;
import com.tooolan.ddd.domain.session.model.UserBean;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

/**
 * 统一用户上下文拦截器
 * 继承 SaInterceptor，在 Sa-Token 认证完成后自动初始化 ContextHolder 上下文
 * <p>
 * 执行顺序：
 * 1. Mock 身份处理（在 Sa-Token 认证之前）
 * 2. Sa-Token 路由拦截 + checkLogin
 * 3. 上下文初始化
 *
 * @author tooolan
 * @since 2026年3月31日
 */
@Slf4j
public class UserContextInterceptor extends SaInterceptor {

    private final UserRepository userRepository;
    private final SecurityAuthProperties authProperties;

    /**
     * 构造函数
     *
     * @param handler        Sa-Token 认证处理器（通常传入 SaRouter 匹配规则）
     * @param userRepository 用户仓储（Mock 登录时查询用户）
     * @param authProperties 鉴权配置属性
     */
    public UserContextInterceptor(SaParamFunction<Object> handler,
                                  UserRepository userRepository,
                                  SecurityAuthProperties authProperties) {
        super(handler);
        this.userRepository = userRepository;
        this.authProperties = authProperties;
    }

    /**
     * 请求前置处理
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @param handler  处理器
     * @return true 表示继续执行
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        // 1. Mock 身份处理（在 Sa-Token 认证之前）
        //    如果是 test-{userId} 格式，自动完成 StpUtil.login，后续 checkLogin 会通过
        this.handleMockLogin();

        // 2. 执行 Sa-Token 路由拦截 + checkLogin
        boolean result = super.preHandle(request, response, handler);

        // 3. 认证通过后，初始化上下文
        this.initializeContext(request);

        return result;
    }

    /**
     * 请求完成后清理资源
     *
     * @param request  HTTP 请求
     * @param response HTTP 响应
     * @param handler  处理器
     * @param ex       异常对象（如果有）
     */
    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                @NonNull Object handler, Exception ex) throws Exception {
        try {
            ContextHolder.clearContext();
        } finally {
            super.afterCompletion(request, response, handler, ex);
        }
    }

    /**
     * Mock 身份处理
     * 开发环境下可通过 test-{userId} 格式的 token 模拟任意用户身份
     */
    private void handleMockLogin() {
        if (BooleanUtil.isFalse(authProperties.isMockEnabled())) {
            return;
        }

        String tokenValue = StpUtil.getTokenValue();
        if (tokenValue == null || !tokenValue.startsWith(MockTokenConstant.MOCK_PREFIX)) {
            return;
        }

        String userIdStr = tokenValue.substring(MockTokenConstant.MOCK_PREFIX.length());
        if (BooleanUtil.isFalse(NumberUtil.isInteger(userIdStr))) {
            throw new SessionException(SessionErrorCode.LOGIN_FAILED);
        }

        Integer userId = Integer.parseInt(userIdStr);

        User user = userRepository.getById(userId)
                .orElseThrow(() -> new SessionException(SessionErrorCode.LOGIN_FAILED));

        StpUtil.login(userId);
        StpUtil.getSession().set(UserBean.Fields.username, user.getUsername());
        StpUtil.getSession().set(UserBean.Fields.nickname, user.getNickName());

        log.warn("【Mock 登录】用户: {}(ID:{})", user.getUsername(), userId);
    }

    /**
     * 初始化上下文
     * 创建 HttpContext 快照，已登录时初始化用户上下文
     */
    private void initializeContext(HttpServletRequest request) {
        try {
            // 1. 初始化 HTTP 上下文（每个请求都有）
            String token = StpUtil.isLogin() ? StpUtil.getTokenValue() : null;
            ContextHolder.setHttpContext(HttpContext.snapshot(request, token));

            // 2. 如果已登录，初始化用户上下文
            if (StpUtil.isLogin()) {
                UserBean userBean = new UserBean(
                        StpUtil.getLoginIdAsInt(),
                        StpUtil.getSession().getString(UserBean.Fields.username),
                        StpUtil.getSession().getString(UserBean.Fields.nickname)
                );
                ContextHolder.setContext(userBean);
            }
        } catch (Exception e) {
            log.warn("初始化上下文失败: {}", e.getMessage());
        }
    }

}
