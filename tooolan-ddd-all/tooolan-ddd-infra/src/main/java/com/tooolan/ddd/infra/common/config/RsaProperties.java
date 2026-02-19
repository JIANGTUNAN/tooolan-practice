package com.tooolan.ddd.infra.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * RSA 密钥配置属性
 * 从 application.yml 读取 security.auth.rsa 配置
 *
 * @author tooolan
 * @since 2026年2月19日
 */
@Data
@Component
@ConfigurationProperties(prefix = "security.auth.rsa")
public class RsaProperties {

    /**
     * RSA 私钥（PEM 格式，Base64 编码）
     * 用于解密前端传输的密码
     */
    private String privateKey;

    /**
     * RSA 公钥（PEM 格式，Base64 编码）
     * 用于提供给前端加密密码
     */
    private String publicKey;

}
