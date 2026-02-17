package com.tooolan.ddd.domain.session.service;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.crypto.digest.BCrypt;
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


    /**
     * 执行登录
     * 校验密码并注册安全上下文
     *
     * @param user     登录用户
     * @param password 明文密码
     * @return token
     * @throws SessionException 密码错误时抛出
     */
    public String login(User user, String password) {
        // 校验密码
        if (BooleanUtil.isFalse(this.verifyPassword(password, user.getPassword()))) {
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

    /**
     * 加密密码（用于用户创建/修改密码）
     *
     * @param rawPassword 明文密码
     * @return 加密后的密码
     */
    public String encodePassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    /**
     * 验证密码
     *
     * @param rawPassword     明文密码
     * @param encodedPassword 加密后的密码
     * @return true-密码正确，false-密码错误
     */
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }

}
