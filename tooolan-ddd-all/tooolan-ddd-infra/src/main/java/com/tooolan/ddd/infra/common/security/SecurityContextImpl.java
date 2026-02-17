package com.tooolan.ddd.infra.common.security;

import cn.dev33.satoken.stp.StpUtil;
import com.tooolan.ddd.domain.session.service.SecurityContext;
import com.tooolan.ddd.domain.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 安全上下文实现
 * 基于 Sa-Token 实现登录状态注册/注销
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Slf4j
@Service
public class SecurityContextImpl implements SecurityContext {

    /**
     * 注册登录状态到安全框架
     *
     * @param user 登录用户
     * @return token
     */
    @Override
    public String registerLogin(User user) {
        StpUtil.login(user.getId());
        StpUtil.getSession().set("username", user.getUsername());
        StpUtil.getSession().set("nickname", user.getNickName());
        return StpUtil.getTokenValue();
    }

    /**
     * 注销安全框架的登录状态
     */
    @Override
    public void unregisterLogin() {
        if (StpUtil.isLogin()) {
            log.info("用户登出成功: userId={}", StpUtil.getLoginIdAsInt());
            StpUtil.logout();
        }
    }

}
