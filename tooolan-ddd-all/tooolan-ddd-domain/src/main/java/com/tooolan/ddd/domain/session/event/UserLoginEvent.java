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
     * 登录令牌
     */
    private final String token;

}
