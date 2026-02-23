package com.tooolan.ddd.app.session.service;

import cn.hutool.core.util.BooleanUtil;
import com.tooolan.ddd.app.session.convert.SessionConvert;
import com.tooolan.ddd.app.session.request.LoginBo;
import com.tooolan.ddd.app.session.response.LoginStatusVo;
import com.tooolan.ddd.app.session.response.LoginVo;
import com.tooolan.ddd.domain.common.context.ContextHolder;
import com.tooolan.ddd.domain.common.context.UserBean;
import com.tooolan.ddd.domain.common.exception.SessionException;
import com.tooolan.ddd.domain.session.constant.SessionErrorCode;
import com.tooolan.ddd.domain.session.event.UserLoginEvent;
import com.tooolan.ddd.domain.session.service.SecurityContext;
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
    private final SecurityContext securityContext;
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

        // 3. 初始化用户上下文（供后续事件监听等使用）
        UserBean userBean = new UserBean(user.getId(), user.getUsername(), user.getNickName());
        ContextHolder.setContext(userBean);

        // 4. 发布事件、返回结果（携带业务数据用于日志记录）
        eventPublisher.publishEvent(UserLoginEvent.of(user, bo));
        return SessionConvert.toLoginVo(token, user);
    }

    /**
     * 用户登出
     */
    public void logout() {
        securityContext.unregisterLogin();
    }

    /**
     * 获取登录状态
     *
     * @return 登录状态
     */
    public LoginStatusVo getStatus() {
        if (BooleanUtil.isFalse(ContextHolder.isLoggedIn())) {
            return SessionConvert.toNotLoggedInVo();
        }
        UserBean userBean = ContextHolder.getUserBean();
        return SessionConvert.toStatusVo(userBean);
    }

}
