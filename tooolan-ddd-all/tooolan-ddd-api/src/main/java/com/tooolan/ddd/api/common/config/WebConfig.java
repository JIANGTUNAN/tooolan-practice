package com.tooolan.ddd.api.common.config;

import com.tooolan.ddd.api.common.web.ContextInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置类
 * 注册上下文拦截器，鉴权拦截器在 SaTokenConfig 中配置
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
     * 只负责上下文管理，不负责鉴权
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(contextInterceptor)
                .addPathPatterns("/**");
    }

}
