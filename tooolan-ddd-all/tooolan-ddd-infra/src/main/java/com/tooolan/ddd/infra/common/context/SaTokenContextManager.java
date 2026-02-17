package com.tooolan.ddd.infra.common.context;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjUtil;
import com.tooolan.ddd.domain.common.context.ContextManager;
import com.tooolan.ddd.domain.common.context.SimpleUserContext;
import com.tooolan.ddd.domain.common.context.UserContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 基于 Sa-Token 的上下文管理器
 * 实现用户上下文的获取、清理和快照功能
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Slf4j
public class SaTokenContextManager implements ContextManager {

    /**
     * 获取当前用户上下文
     *
     * @return 当前用户上下文，未登录时返回未登录状态的上下文
     */
    @Override
    public UserContext getCurrentContext() {
        try {
            if (!StpUtil.isLogin()) {
                return new SimpleUserContext();
            }
            return new SimpleUserContext(
                    StpUtil.getLoginIdAsInt(),
                    (String) StpUtil.getSession().get("username"),
                    (String) StpUtil.getSession().get("nickname"),
                    StpUtil.getTokenValue()
            );
        } catch (Exception e) {
            // 异常情况返回未登录状态
            return new SimpleUserContext();
        }
    }

    /**
     * 初始化用户上下文
     * Sa-Token 模式下不支持手动初始化
     *
     * @param context 用户上下文
     */
    @Override
    public void initContext(UserContext context) {
        log.warn("Sa-Token 模式下不支持手动初始化上下文");
    }

    /**
     * 清空当前用户上下文
     */
    @Override
    public void clearContext() {
        if (StpUtil.isLogin()) {
            StpUtil.logout();
        }
    }

    /**
     * 构建当前上下文的快照
     * 用于在子线程中恢复上下文
     *
     * @return 上下文快照
     */
    @Override
    public UserContext snapshot() {
        return getCurrentContext();
    }

    /**
     * 从快照恢复上下文
     * Sa-Token 模式下，子线程可以通过 token 继承会话
     *
     * @param snapshot 上下文快照
     */
    @Override
    public void initFromSnapshot(UserContext snapshot) {
        log.debug("Sa-Token 模式下从快照恢复上下文: userId={}",
                ObjUtil.isNotNull(snapshot) ? snapshot.getUserId() : null);
    }

}
