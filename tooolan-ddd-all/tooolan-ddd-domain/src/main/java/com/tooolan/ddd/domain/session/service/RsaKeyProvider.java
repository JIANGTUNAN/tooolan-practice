package com.tooolan.ddd.domain.session.service;

/**
 * RSA 密钥提供者接口
 * 由基础设施层实现，提供 RSA 密钥
 *
 * @author tooolan
 * @since 2026年2月19日
 */
public interface RsaKeyProvider {

    /**
     * 获取 RSA 私钥
     *
     * @return RSA 私钥（PEM 格式）
     */
    String getPrivateKey();

    /**
     * 获取 RSA 公钥
     *
     * @return RSA 公钥（PEM 格式）
     */
    String getPublicKey();

}
