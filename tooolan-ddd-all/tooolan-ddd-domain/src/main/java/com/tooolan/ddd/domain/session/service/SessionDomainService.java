package com.tooolan.ddd.domain.session.service;

import cn.hutool.core.util.BooleanUtil;
import com.tooolan.ddd.domain.common.annotation.DomainService;
import com.tooolan.ddd.domain.common.exception.SessionException;
import com.tooolan.ddd.domain.session.constant.SessionErrorCode;
import com.tooolan.ddd.domain.user.model.User;
import lombok.RequiredArgsConstructor;

/**
 * 会话 领域服务
 * 提供会话相关的核心业务规则
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@DomainService
@RequiredArgsConstructor
public class SessionDomainService {

    private final SecurityContext securityContext;
    private final PasswordEncryptor passwordEncryptor;


    /**
     * 执行登录
     * 校验密码并注册安全上下文
     *
     * @param user              登录用户
     * @param encryptedPassword RSA 加密的密码
     * @return token
     * @throws SessionException 密码错误时抛出
     */
    public String login(User user, String encryptedPassword) {
        // RSA 解密获取 SHA256 摘要
        String sha256Password = passwordEncryptor.decryptPassword(encryptedPassword);

        // 校验密码
        if (BooleanUtil.isFalse(passwordEncryptor.verifyPassword(sha256Password, user.getPassword()))) {
            throw new SessionException(SessionErrorCode.PASSWORD_MISMATCH);
        }

        // 注册安全上下文
        return securityContext.registerLogin(user);
    }

    /**
     * 执行登出
     */
    public void logout() {
        securityContext.unregisterLogin();
    }

}
