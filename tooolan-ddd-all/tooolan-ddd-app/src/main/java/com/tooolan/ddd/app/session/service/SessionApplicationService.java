package com.tooolan.ddd.app.session.service;

import com.tooolan.ddd.app.session.convert.SessionConvert;
import com.tooolan.ddd.app.session.request.LoginBo;
import com.tooolan.ddd.app.session.response.LoginStatusVo;
import com.tooolan.ddd.app.session.response.LoginVo;
import com.tooolan.ddd.domain.common.context.ContextHolder;
import com.tooolan.ddd.domain.common.exception.SessionException;
import com.tooolan.ddd.domain.session.constant.SessionErrorCode;
import com.tooolan.ddd.domain.session.event.UserLoginEvent;
import com.tooolan.ddd.domain.session.service.SessionDomainService;
import com.tooolan.ddd.domain.user.model.User;
import com.tooolan.ddd.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;


    /**
     * 用户登录
     *
     * @param bo 登录业务对象
     * @return 登录结果
     * @throws SessionException 用户不存在或密码错误时抛出
     */
    public LoginVo login(LoginBo bo) {
        // 1. 应用层查询用户
        User user = userRepository.getUserByUsername(bo.getUsername())
                .orElseThrow(() -> new SessionException(SessionErrorCode.LOGIN_FAILED));

        // 2. 调用领域服务执行登录（密码校验、注册上下文）
        String token = sessionDomainService.login(user, bo.getPassword());

        // 3. 发布事件、返回结果
        eventPublisher.publishEvent(UserLoginEvent.of(user, token));
        return SessionConvert.toLoginVo(token, user);
    }

    /**
     * 用户登出
     */
    public void logout() {
        sessionDomainService.logout();
    }

    /**
     * 获取登录状态
     *
     * @return 登录状态
     */
    public LoginStatusVo getStatus() {
        if (!ContextHolder.isLoggedIn()) {
            return SessionConvert.toNotLoggedInVo();
        }
        Integer userId = ContextHolder.getUserId();
        User user = userRepository.getUser(userId).orElse(null);
        return SessionConvert.toStatusVo(user, ContextHolder.getSessionId());
    }

    /**
     * 获取当前用户信息
     *
     * @return 当前用户信息
     * @throws SessionException 未登录时抛出
     */
    public LoginStatusVo getCurrentUser() {
        ContextHolder.requireUserId();
        Integer userId = ContextHolder.getUserId();
        User user = userRepository.getUser(userId)
                .orElseThrow(() -> new SessionException(SessionErrorCode.LOGIN_FAILED));
        return SessionConvert.toStatusVo(user, ContextHolder.getSessionId());
    }

}
