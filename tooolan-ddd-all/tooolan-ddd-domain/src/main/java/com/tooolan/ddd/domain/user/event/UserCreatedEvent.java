package com.tooolan.ddd.domain.user.event;

import com.tooolan.ddd.domain.user.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 用户创建领域事件
 * 当用户成功创建时发布此事件
 *
 * @author tooolan
 * @since 2026年2月13日
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public class UserCreatedEvent {

    /**
     * 事件源
     */
    private final Object source = this;

    /**
     * 创建的用户领域模型
     */
    private final User user;

}
