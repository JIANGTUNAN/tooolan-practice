package com.tooolan.ddd.domain.common.context;

/**
 * 上下文管理器接口
 * 负责用户上下文的初始化、获取、清理和快照
 *
 * @author tooolan
 * @since 2026年2月17日
 */
public interface ContextManager {

    /**
     * 获取当前用户上下文
     *
     * @return 当前用户上下文，未登录时返回未登录状态的上下文
     */
    UserContext getCurrentContext();

    /**
     * 初始化用户上下文
     *
     * @param context 用户上下文
     */
    void initContext(UserContext context);

    /**
     * 清空当前用户上下文
     */
    void clearContext();

    /**
     * 构建当前上下文的快照
     * 用于在子线程中恢复上下文
     *
     * @return 上下文快照
     */
    UserContext snapshot();

    /**
     * 从快照恢复上下文
     *
     * @param snapshot 上下文快照
     */
    void initFromSnapshot(UserContext snapshot);

}
