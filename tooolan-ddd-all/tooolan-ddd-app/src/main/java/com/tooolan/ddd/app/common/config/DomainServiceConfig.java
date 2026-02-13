package com.tooolan.ddd.app.common.config;

import com.tooolan.ddd.domain.common.annotation.DomainService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * 领域服务配置
 * 配置 Spring 识别领域层的自定义 @DomainService 注解
 * 实现依赖倒置：领域层不依赖 Spring，由应用层告知如何识别
 *
 * @author tooolan
 * @since 2026年2月12日
 */
@Configuration
@ComponentScan(
        basePackages = "com.tooolan.ddd.domain",
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = {DomainService.class}
        ),
        useDefaultFilters = false  // 不使用默认过滤器，只识别自定义注解
)
public class DomainServiceConfig {
}
