package com.tooolan.ddd.domain.common.context;

import cn.hutool.core.util.ObjUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 简单的用户上下文实现
 * 用于快照和子线程传递
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserContext implements UserContext {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 从另一个 UserContext 复制构造
     *
     * @param other 其他用户上下文
     */
    public SimpleUserContext(UserContext other) {
        if (ObjUtil.isNotNull(other)) {
            this.userId = other.getUserId();
            this.username = other.getUsername();
            this.nickname = other.getNickname();
            this.sessionId = other.getSessionId();
        }
    }

    @Override
    public boolean isLoggedIn() {
        return ObjUtil.isNotNull(userId);
    }

}
