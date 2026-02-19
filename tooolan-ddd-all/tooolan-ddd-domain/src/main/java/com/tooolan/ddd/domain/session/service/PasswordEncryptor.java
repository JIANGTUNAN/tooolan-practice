package com.tooolan.ddd.domain.session.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.BCrypt;
import com.tooolan.ddd.domain.common.annotation.DomainService;
import com.tooolan.ddd.domain.common.exception.SessionException;
import com.tooolan.ddd.domain.session.constant.SessionErrorCode;
import lombok.RequiredArgsConstructor;

/**
 * 密码加密器 领域服务
 * 处理密码的加密、解密和验证
 *
 * @author tooolan
 * @since 2026年2月19日
 */
@DomainService
@RequiredArgsConstructor
public class PasswordEncryptor {

    private final RsaKeyProvider rsaKeyProvider;


    /**
     * 解密前端传输的密码
     * RSA 解密获取 SHA256 摘要
     *
     * @param encryptedPassword RSA 加密的密码
     * @return SHA256 摘要
     */
    public String decryptPassword(String encryptedPassword) {
        if (StrUtil.isBlank(encryptedPassword)) {
            throw new SessionException(SessionErrorCode.PASSWORD_REQUIRED);
        }

        try {
            String privateKey = rsaKeyProvider.getPrivateKey();
            RSA rsa = new RSA(privateKey, null);
            return rsa.decryptStr(encryptedPassword, KeyType.PrivateKey);
        } catch (Exception e) {
            throw new SessionException(SessionErrorCode.PASSWORD_DECRYPT_FAILED, e);
        }
    }

    /**
     * 加密密码（BCrypt 哈希）
     * 用于用户创建/修改密码时存储
     *
     * @param sha256Password SHA256 摘要
     * @return BCrypt 哈希后的密码
     */
    public String encodePassword(String sha256Password) {
        return BCrypt.hashpw(sha256Password, BCrypt.gensalt());
    }

    /**
     * 验证密码
     *
     * @param sha256Password  SHA256 摘要
     * @param encodedPassword BCrypt 哈希后的密码
     * @return true-密码正确，false-密码错误
     */
    public boolean verifyPassword(String sha256Password, String encodedPassword) {
        return BCrypt.checkpw(sha256Password, encodedPassword);
    }

}
