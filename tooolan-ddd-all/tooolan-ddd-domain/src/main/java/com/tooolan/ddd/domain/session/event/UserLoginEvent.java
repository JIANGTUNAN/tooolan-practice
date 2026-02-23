package com.tooolan.ddd.domain.session.event;

import com.tooolan.ddd.domain.user.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 用户登录领域事件
 * 当用户成功登录时发布此事件
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public class UserLoginEvent {

    /**
     * 事件源
     */
    private final Object source = this;

    /**
     * 登录的用户领域模型
     */
    private final User user;

    /**
     * 业务数据（携带登录请求参数）
     * 用于日志记录，在 app 层进行 JSON 序列化
     */
    private final Object businessData;

}
