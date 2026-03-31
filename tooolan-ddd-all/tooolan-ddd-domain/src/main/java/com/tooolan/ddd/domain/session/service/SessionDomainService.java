package com.tooolan.ddd.domain.session.service;

import cn.hutool.core.util.BooleanUtil;
import com.tooolan.ddd.domain.common.annotation.DomainService;
import com.tooolan.ddd.domain.session.constant.SessionErrorCode;
import com.tooolan.ddd.domain.session.exception.SessionException;
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

    private final PasswordService passwordService;

    /**
     * 验证密码
     *
     * @param user              登录用户
     * @param encryptedPassword RSA 加密的密码
     * @throws SessionException 密码错误时抛出
     */
    public void verifyPassword(User user, String encryptedPassword) {
        // RSA 解密获取 SHA256 摘要
        String sha256Password = passwordService.decryptPassword(encryptedPassword);

        // 校验密码
        if (BooleanUtil.isFalse(passwordService.verifyPassword(sha256Password, user.getPassword()))) {
            throw new SessionException(SessionErrorCode.LOGIN_FAILED);
        }
    }

}
