package com.tooolan.ddd.api.common.config;

import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.tooolan.ddd.api.common.web.UserContextInterceptor;
import com.tooolan.ddd.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置类
 * 注册 UserContextInterceptor，统一处理 Sa-Token 路由拦截、登录认证和上下文初始化
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final SecurityAuthProperties authProperties;
    private final UserRepository userRepository;

    /**
     * 注册拦截器
     * <p>
     * UserContextInterceptor 继承 SaInterceptor，同时处理：
     * 1. Mock 身份处理（test-{userId} 格式 token）
     * 2. Sa-Token 路由拦截和登录认证
     * 3. 用户上下文的初始化和清理
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserContextInterceptor(handle -> {
            // 使用 SaRouter 匹配所有路径，排除白名单，然后校验登录
            SaRouter
                    .match("/**")
                    .notMatch(authProperties.getPublicPaths())
                    .check(r -> StpUtil.checkLogin());
        }, userRepository, authProperties)).addPathPatterns("/**");
    }

}
