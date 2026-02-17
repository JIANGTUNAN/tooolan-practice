package com.tooolan.ddd.infra.common.security;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Sa-Token 权限验证实现
 * 目前返回空列表，后续可根据业务需求实现权限和角色验证
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合
     *
     * @param loginId   登录id
     * @param loginType 多账户体系时的多账户标识
     * @return 权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // TODO: 后续实现权限验证
        return Collections.emptyList();
    }

    /**
     * 返回一个账号所拥有的角色标识集合
     *
     * @param loginId   登录id
     * @param loginType 多账户体系时的多账户标识
     * @return 角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // TODO: 后续实现角色验证
        return Collections.emptyList();
    }

}
