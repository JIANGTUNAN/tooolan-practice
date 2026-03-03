package com.tooolan.ddd.domain.session.config;

import com.tooolan.ddd.domain.common.annotation.PropertiesConfig;
import lombok.Data;

/**
 * RSA 配置类
 * RSA 加密解密密钥配置
 *
 * @author tooolan
 * @since 2026年3月3日
 */
@Data
@PropertiesConfig(prefix = "security.auth.rsa")
public class RsaConfig {

    /**
     * RSA 私钥（Base64 编码）
     */
    private String privateKey;

    /**
     * RSA 公钥（Base64 编码）
     */
    private String publicKey;

}
