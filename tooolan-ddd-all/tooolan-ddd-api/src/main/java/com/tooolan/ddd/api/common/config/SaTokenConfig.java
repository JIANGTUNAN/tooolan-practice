package com.tooolan.ddd.api.common.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 路由拦截鉴权配置
 * 负责登录校验、角色/权限校验等鉴权逻辑
 *
 * @author tooolan
 * @since 2026年3月8日
 */
@Configuration
@RequiredArgsConstructor
public class SaTokenConfig implements WebMvcConfigurer {

    private final SecurityAuthProperties authProperties;

    /**
     * 注册 Sa-Token 拦截器
     * 拦截所有路径，排除白名单，执行登录校验
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> {
            // 如果禁用鉴权，直接放行
            if (!authProperties.isEnabled()) {
                return;
            }

            // 登录校验：拦截所有路径，排除白名单
            SaRouter
                    .match("/**")
                    .notMatch(authProperties.getPublicPaths())
                    .check(r -> StpUtil.checkLogin());

            // 后续可扩展：根据路由划分模块，不同模块不同鉴权
            // SaRouter.match("/admin/**", r -> StpUtil.checkRole("admin"));
            // SaRouter.match("/user/**", r -> StpUtil.checkPermission("user"));
        })).addPathPatterns("/**");
    }

}
