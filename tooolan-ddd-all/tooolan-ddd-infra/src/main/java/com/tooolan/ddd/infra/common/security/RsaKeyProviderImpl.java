package com.tooolan.ddd.infra.common.security;

import cn.hutool.core.util.StrUtil;
import com.tooolan.ddd.domain.session.service.RsaKeyProvider;
import com.tooolan.ddd.infra.common.config.RsaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * RSA 密钥提供者实现
 * 从配置文件读取 RSA 密钥
 *
 * @author tooolan
 * @since 2026年2月19日
 */
@Component
@RequiredArgsConstructor
public class RsaKeyProviderImpl implements RsaKeyProvider {

    private final RsaProperties rsaProperties;


    /**
     * 获取 RSA 私钥
     *
     * @return 结果
     * @throws IllegalStateException 私钥未配置时抛出
     */
    @Override
    public String getPrivateKey() {
        String key = rsaProperties.getPrivateKey();
        if (StrUtil.isBlank(key)) {
            throw new IllegalStateException("RSA 私钥未配置，请在 application.yml 中配置 security.auth.rsa.private-key");
        }
        return key;
    }

    /**
     * 获取 RSA 公钥
     *
     * @return 结果
     * @throws IllegalStateException 公钥未配置时抛出
     */
    @Override
    public String getPublicKey() {
        String key = rsaProperties.getPublicKey();
        if (StrUtil.isBlank(key)) {
            throw new IllegalStateException("RSA 公钥未配置，请在 application.yml 中配置 security.auth.rsa.public-key");
        }
        return key;
    }

}
