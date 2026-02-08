package com.tooolan.practice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * DDNS配置类
 * 用于配置主力IP和容灾IP
 *
 * @author tooolan
 * @since 2026年2月7日
 */
@Configuration
@ConfigurationProperties(prefix = "ddns")
@Data
public class DdnsConfig {

    /**
     * 主力IP（默认使用）
     */
    private String primaryIp;

    /**
     * 容灾IP（主力IP宕机时切换）
     */
    private String backupIp;

}
