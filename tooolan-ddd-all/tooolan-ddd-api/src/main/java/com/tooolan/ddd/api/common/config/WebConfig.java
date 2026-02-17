package com.tooolan.ddd.api.common.config;

import com.tooolan.ddd.api.common.web.ContextInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置类
 * 注册拦截器，路径控制统一在配置文件中管理
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
     * 拦截所有请求，路径白名单由配置文件 security.auth.public-paths 管理
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(contextInterceptor)
                .addPathPatterns("/**");
    }

}
