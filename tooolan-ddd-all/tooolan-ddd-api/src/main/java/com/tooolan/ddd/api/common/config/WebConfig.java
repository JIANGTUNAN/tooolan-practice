package com.tooolan.ddd.api.common.config;

import com.tooolan.ddd.api.common.web.ContextInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置类
 * 定义拦截器和白名单
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final ContextInterceptor contextInterceptor;


    /**
     * 注册上下文拦截器
     * 拦截所有请求，在请求进入时初始化用户上下文，请求结束时清理。
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(contextInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/session/login",
                        "/error",
                        "/favicon.ico"
                );
    }

}
