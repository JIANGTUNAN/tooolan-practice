package com.tooolan.ddd.domain.common.context;

import cn.hutool.core.util.ObjUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.tooolan.ddd.domain.common.exception.SessionException;
import com.tooolan.ddd.domain.session.constant.SessionErrorCode;
import lombok.experimental.UtilityClass;

/**
 * 上下文持有者
 * <p>
 * 提供全局访问入口，基于 TTL（TransmittableThreadLocal）存储用户上下文，
 * 自动传递到子线程和线程池。
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@UtilityClass
public class ContextHolder {

    /**
     * TTL 存储，自动传递到子线程
     */
    private final TransmittableThreadLocal<UserBean> CONTEXT_HOLDER = new TransmittableThreadLocal<>();


    /**
     * 获取当前用户ID
     *
     * @return 用户ID
     * @throws SessionException 如果未登录
     */
    public Integer getUserId() {
        UserBean context = getContextInternal();
        if (ObjUtil.isNull(context) || ObjUtil.isNull(context.getUserId())) {
            throw new SessionException(SessionErrorCode.NOT_LOGIN);
        }
        return context.getUserId();
    }

    /**
     * 获取当前用户名
     *
     * @return 用户名
     * @throws SessionException 如果未登录
     */
    public String getUsername() {
        UserBean context = getContextInternal();
        if (ObjUtil.isNull(context) || ObjUtil.isNull(context.getUsername())) {
            throw new SessionException(SessionErrorCode.NOT_LOGIN);
        }
        return context.getUsername();
    }

    /**
     * 获取当前用户昵称
     *
     * @return 用户昵称
     * @throws SessionException 如果未登录
     */
    public String getNickname() {
        UserBean context = getContextInternal();
        if (ObjUtil.isNull(context) || ObjUtil.isNull(context.getNickname())) {
            throw new SessionException(SessionErrorCode.NOT_LOGIN);
        }
        return context.getNickname();
    }

    /**
     * 获取当前会话ID
     *
     * @return 会话ID
     * @throws SessionException 如果未登录
     */
    public String getSessionId() {
        UserBean context = getContextInternal();
        if (ObjUtil.isNull(context) || ObjUtil.isNull(context.getSessionId())) {
            throw new SessionException(SessionErrorCode.NOT_LOGIN);
        }
        return context.getSessionId();
    }

    /**
     * 判断当前是否已登录
     *
     * @return true 已登录，false 未登录
     */
    public boolean isLoggedIn() {
        UserBean context = getContextInternal();
        return ObjUtil.isNotNull(context);
    }

    /**
     * 获取当前用户上下文
     *
     * @return 用户上下文
     * @throws SessionException 如果未登录
     */
    public UserBean getUserBean() {
        UserBean context = getContextInternal();
        if (ObjUtil.isNull(context)) {
            throw new SessionException(SessionErrorCode.NOT_LOGIN);
        }
        return context;
    }

    /**
     * 初始化系统上下文
     * 用于定时任务、消息队列消费者等非用户触发的场景
     */
    public void initSystemContext() {
        UserBean systemContext = new UserBean(-1, "system", "系统任务", null);
        CONTEXT_HOLDER.set(systemContext);
    }

    /**
     * 设置当前用户上下文
     *
     * @param context 用户上下文
     */
    public void setContext(UserBean context) {
        CONTEXT_HOLDER.set(context);
    }

    /**
     * 清空当前用户上下文
     */
    public void clearContext() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * 获取当前用户上下文（内部使用）
     *
     * @return 用户上下文，未登录返回 null
     */
    private UserBean getContextInternal() {
        return CONTEXT_HOLDER.get();
    }

}
