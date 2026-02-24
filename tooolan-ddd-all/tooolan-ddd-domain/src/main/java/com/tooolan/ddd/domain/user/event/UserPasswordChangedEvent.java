package com.tooolan.ddd.domain.user.event;

import com.tooolan.ddd.domain.user.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 用户密码修改领域事件
 * 当用户密码成功修改后发布此事件
 *
 * @author tooolan
 * @since 2026年2月24日
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public class UserPasswordChangedEvent {

    /**
     * 事件源
     */
    private final Object source = this;

    /**
     * 密码修改后的用户领域模型
     */
    private final User user;

}
