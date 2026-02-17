package com.tooolan.ddd.domain.common.context;

import cn.hutool.core.util.ObjUtil;
import com.tooolan.ddd.domain.common.constant.CommonErrorCode;
import com.tooolan.ddd.domain.common.exception.DomainException;
import lombok.Setter;
import lombok.experimental.UtilityClass;

/**
 * 上下文持有者
 * 提供全局访问入口，支持子线程继承
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@UtilityClass
public class ContextHolder {

    private final InheritableThreadLocal<UserContext> CONTEXT_HOLDER = new InheritableThreadLocal<>();

    @Setter
    private volatile ContextManager contextManager;


    /**
     * 获取当前用户ID
     *
     * @return 用户ID，未登录返回 null
     */
    public Integer getUserId() {
        UserContext context = getContext();
        return ObjUtil.isNotNull(context) ? context.getUserId() : null;
    }

    /**
     * 获取当前用户ID，如果未登录则抛出异常
     *
     * @return 用户ID
     * @throws DomainException 如果未登录
     */
    public Integer requireUserId() {
        Integer userId = getUserId();
        if (ObjUtil.isNull(userId)) {
            throw new DomainException(CommonErrorCode.UNAUTHORIZED);
        }
        return userId;
    }

    /**
     * 获取当前用户名
     *
     * @return 用户名，未登录返回 null
     */
    public String getUsername() {
        UserContext context = getContext();
        return ObjUtil.isNotNull(context) ? context.getUsername() : null;
    }

    /**
     * 获取当前用户昵称
     *
     * @return 用户昵称，未登录返回 null
     */
    public String getNickname() {
        UserContext context = getContext();
        return ObjUtil.isNotNull(context) ? context.getNickname() : null;
    }

    /**
     * 判断当前是否已登录
     *
     * @return true 已登录，false 未登录
     */
    public boolean isLoggedIn() {
        UserContext context = getContext();
        return ObjUtil.isNotNull(context) && context.isLoggedIn();
    }

    /**
     * 获取当前会话ID
     *
     * @return 会话ID，未登录返回 null
     */
    public String getSessionId() {
        UserContext context = getContext();
        return ObjUtil.isNotNull(context) ? context.getSessionId() : null;
    }

    /**
     * 获取当前用户上下文
     * 优先从 ContextManager 获取（sa-token 模式）
     * 如果没有 ContextManager 则从 ThreadLocal 获取（快照模式）
     *
     * @return 用户上下文
     */
    public UserContext getContext() {
        if (ObjUtil.isNotNull(contextManager)) {
            return contextManager.getCurrentContext();
        }
        return CONTEXT_HOLDER.get();
    }

    /**
     * 设置当前用户上下文（用于快照恢复）
     *
     * @param context 用户上下文
     */
    public void setContext(UserContext context) {
        CONTEXT_HOLDER.set(context);
    }

    /**
     * 清空当前用户上下文
     */
    public void clearContext() {
        if (ObjUtil.isNotNull(contextManager)) {
            contextManager.clearContext();
        }
        CONTEXT_HOLDER.remove();
    }

    /**
     * 构建当前上下文的快照
     * 用于在异步任务中传递上下文
     *
     * @return 上下文快照
     */
    public UserContext snapshot() {
        if (ObjUtil.isNotNull(contextManager)) {
            return contextManager.snapshot();
        }
        UserContext context = CONTEXT_HOLDER.get();
        return ObjUtil.isNotNull(context) ? new SimpleUserContext(context) : null;
    }

    /**
     * 从快照恢复上下文
     * 用于在异步任务开始时恢复上下文
     *
     * @param snapshot 上下文快照
     */
    public void initFromSnapshot(UserContext snapshot) {
        if (ObjUtil.isNotNull(contextManager)) {
            contextManager.initFromSnapshot(snapshot);
        } else {
            CONTEXT_HOLDER.set(snapshot);
        }
    }

}
