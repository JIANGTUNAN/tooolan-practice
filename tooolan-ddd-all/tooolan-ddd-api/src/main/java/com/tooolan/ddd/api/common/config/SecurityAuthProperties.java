package com.tooolan.ddd.api.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权配置属性
 * 从 application.yml 读取 security.auth 配置
 *
 * @author tooolan
 * @since 2026年2月18日
 */
@Data
@Component
@ConfigurationProperties(prefix = "security.auth")
public class SecurityAuthProperties {

    /**
     * 是否启用登录校验
     * 开发环境可设置为 false 关闭鉴权
     */
    private boolean enabled = true;

    /**
     * 公开路径白名单
     * 匹配这些路径的请求不需要登录校验
     * 支持 Ant 风格路径模式（如 /api/**）
     */
    private List<String> publicPaths = new ArrayList<>();

}
