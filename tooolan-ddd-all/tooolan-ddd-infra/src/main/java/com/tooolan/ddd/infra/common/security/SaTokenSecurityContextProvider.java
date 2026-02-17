package com.tooolan.ddd.infra.common.security;

import cn.dev33.satoken.stp.StpUtil;
import com.tooolan.ddd.domain.common.context.UserContextBean;
import com.tooolan.ddd.domain.session.service.SecurityContextProvider;
import org.springframework.stereotype.Component;

/**
 * 基于 Sa-Token 的安全上下文提供者
 * 实现 {@link SecurityContextProvider} 接口，从 Sa-Token 框架获取当前登录用户信息
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Component
public class SaTokenSecurityContextProvider implements SecurityContextProvider {

    /**
     * 判断当前请求是否已登录
     *
     * @return true 已登录，false 未登录
     */
    @Override
    public boolean isLogin() {
        return StpUtil.isLogin();
    }

    /**
     * 获取当前登录用户ID
     *
     * @return 用户ID
     */
    @Override
    public Integer getUserId() {
        return StpUtil.getLoginIdAsInt();
    }

    /**
     * 获取当前登录用户名
     *
     * @return 用户名
     */
    @Override
    public String getUsername() {
        return (String) StpUtil.getSession().get(UserContextBean.Fields.username);
    }

    /**
     * 获取当前登录用户昵称
     *
     * @return 用户昵称
     */
    @Override
    public String getNickname() {
        return (String) StpUtil.getSession().get(UserContextBean.Fields.nickname);
    }

    /**
     * 获取当前会话的 Token
     *
     * @return Token 值
     */
    @Override
    public String getToken() {
        return StpUtil.getTokenValue();
    }

}
