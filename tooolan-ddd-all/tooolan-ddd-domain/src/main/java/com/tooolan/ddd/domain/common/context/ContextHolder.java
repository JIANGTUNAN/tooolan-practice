package com.tooolan.ddd.domain.common.context;

import cn.hutool.core.util.ObjUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.tooolan.ddd.domain.common.exception.SessionException;
import com.tooolan.ddd.domain.session.constant.SessionErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

/**
 * 上下文持有者
 * <p>
 * 提供全局访问入口，基于 TTL（TransmittableThreadLocal）存储用户上下文和 HTTP 请求上下文，
 * 自动传递到子线程和线程池。
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@UtilityClass
public class ContextHolder {

    /**
     * 用户上下文 TTL 存储，自动传递到子线程
     */
    private final TransmittableThreadLocal<UserBean> USER_CONTEXT_HOLDER = new TransmittableThreadLocal<>();

    /**
     * HTTP 请求上下文 TTL 存储，自动传递到子线程
     */
    private final TransmittableThreadLocal<HttpContext> HTTP_CONTEXT_HOLDER = new TransmittableThreadLocal<>();


    // ==================== 用户上下文相关方法 ====================

    /**
     * 获取当前用户ID
     *
     * @return 用户ID
     * @throws SessionException 如果未登录
     */
    public Integer getUserId() {
        UserBean context = getUserContextInternal();
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
        UserBean context = getUserContextInternal();
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
        UserBean context = getUserContextInternal();
        if (ObjUtil.isNull(context) || ObjUtil.isNull(context.getNickname())) {
            throw new SessionException(SessionErrorCode.NOT_LOGIN);
        }
        return context.getNickname();
    }

    /**
     * 判断当前是否已登录
     *
     * @return true 已登录，false 未登录
     */
    public boolean isLoggedIn() {
        UserBean context = getUserContextInternal();
        return ObjUtil.isNotNull(context);
    }

    /**
     * 获取当前用户上下文
     *
     * @return 用户上下文
     * @throws SessionException 如果未登录
     */
    public UserBean getUserBean() {
        UserBean context = getUserContextInternal();
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
        UserBean systemContext = new UserBean(-1, "system", "系统任务");
        USER_CONTEXT_HOLDER.set(systemContext);
    }

    /**
     * 设置当前用户上下文
     *
     * @param context 用户上下文
     */
    public void setContext(UserBean context) {
        USER_CONTEXT_HOLDER.set(context);
    }

    // ==================== HTTP 上下文相关方法 ====================

    /**
     * 获取 HTTP 请求上下文
     *
     * @return HTTP 请求上下文，可能为 null（非 HTTP 请求场景）
     */
    public HttpContext getHttpContext() {
        return HTTP_CONTEXT_HOLDER.get();
    }

    /**
     * 设置 HTTP 请求上下文
     *
     * @param httpContext HTTP 请求上下文
     */
    public void setHttpContext(HttpContext httpContext) {
        HTTP_CONTEXT_HOLDER.set(httpContext);
    }

    /**
     * 获取 HTTP 请求对象
     *
     * @return HTTP 请求对象，可能为 null
     */
    public HttpServletRequest getRequest() {
        HttpContext httpContext = HTTP_CONTEXT_HOLDER.get();
        return ObjUtil.isNotNull(httpContext) ? httpContext.getRequest() : null;
    }

    /**
     * 获取 HTTP 响应对象
     *
     * @return HTTP 响应对象，可能为 null
     */
    public HttpServletResponse getResponse() {
        HttpContext httpContext = HTTP_CONTEXT_HOLDER.get();
        return ObjUtil.isNotNull(httpContext) ? httpContext.getResponse() : null;
    }

    /**
     * 获取身份令牌
     *
     * @return 身份令牌，未登录时返回 null
     */
    public String getToken() {
        HttpContext httpContext = HTTP_CONTEXT_HOLDER.get();
        return ObjUtil.isNotNull(httpContext) ? httpContext.getToken() : null;
    }

    // ==================== 通用方法 ====================

    /**
     * 清空所有上下文
     */
    public void clearContext() {
        USER_CONTEXT_HOLDER.remove();
        HTTP_CONTEXT_HOLDER.remove();
    }

    /**
     * 获取当前用户上下文（内部使用）
     *
     * @return 用户上下文，未登录返回 null
     */
    private UserBean getUserContextInternal() {
        return USER_CONTEXT_HOLDER.get();
    }

}
