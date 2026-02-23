package com.tooolan.ddd.domain.user.event;

import com.tooolan.ddd.domain.user.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 用户更新领域事件
 * 当用户信息成功更新后发布此事件
 *
 * @author tooolan
 * @since 2026年2月14日
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public class UserUpdatedEvent {

    /**
     * 事件源
     */
    private final Object source = this;

    /**
     * 更新后的用户领域模型
     */
    private final User user;

    /**
     * 业务数据（携带更新用户的请求参数）
     * 用于日志记录，在 app 层进行 JSON 序列化
     */
    private final Object businessData;

}
