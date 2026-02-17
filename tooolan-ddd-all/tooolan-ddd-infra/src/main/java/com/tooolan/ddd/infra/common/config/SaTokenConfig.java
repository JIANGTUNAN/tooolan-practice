package com.tooolan.ddd.infra.common.config;

import com.tooolan.ddd.domain.common.context.ContextHolder;
import com.tooolan.ddd.domain.common.context.ContextManager;
import com.tooolan.ddd.infra.common.context.SaTokenContextManager;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sa-Token 配置类
 * 创建 ContextManager 并注入到 ContextHolder
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Slf4j
@Configuration
public class SaTokenConfig {

    /**
     * 创建基于 Sa-Token 的上下文管理器
     */
    @Bean
    public ContextManager contextManager() {
        return new SaTokenContextManager();
    }

    /**
     * 应用启动后将 ContextManager 注入到 ContextHolder
     */
    @PostConstruct
    public void initContextHolder() {
        ContextHolder.setContextManager(contextManager());
        log.info("Sa-Token 上下文管理器已初始化");
    }

}
