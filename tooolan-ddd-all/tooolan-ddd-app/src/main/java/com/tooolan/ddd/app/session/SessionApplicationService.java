package com.tooolan.ddd.app.session;

import cn.hutool.core.util.BooleanUtil;
import com.tooolan.ddd.app.session.convert.SessionAppConverter;
import com.tooolan.ddd.app.session.response.LoginStatusVo;
import com.tooolan.ddd.domain.common.context.ContextHolder;
import com.tooolan.ddd.domain.session.constant.SessionErrorCode;
import com.tooolan.ddd.domain.session.exception.SessionException;
import com.tooolan.ddd.domain.session.model.UserBean;
import com.tooolan.ddd.domain.session.service.SessionDomainService;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 会话应用服务
 * 提供会话相关的业务编排
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Service
@RequiredArgsConstructor
public class SessionApplicationService {

    private final UserRepository userRepository;
    private final SessionDomainService sessionDomainService;
    private final SessionAppConverter sessionAppConverter;

    /**
     * 获取登录状态
     *
     * @return 登录状态
     */
    public LoginStatusVo getStatus() {
        if (BooleanUtil.isFalse(ContextHolder.isLoggedIn())) {
            return sessionAppConverter.toNotLoggedInVo();
        }
        UserBean userBean = ContextHolder.getUserBean();
        return sessionAppConverter.toStatusVo(userBean);
    }

    /**
     * 认证用户，返回用户信息
     * 用于 Controller 层调用，验证密码后返回用户信息
     *
     * @param username          用户名
     * @param encryptedPassword RSA 加密的密码
     * @return 用户信息
     * @throws SessionException 用户不存在或密码错误时抛出
     */
    public User authenticate(String username, String encryptedPassword) {
        // 1. 查询用户
        User user = userRepository.getByUsername(username)
                .orElseThrow(() -> new SessionException(SessionErrorCode.LOGIN_FAILED));

        // 2. 验证密码
        sessionDomainService.verifyPassword(user, encryptedPassword);

        return user;
    }

}
