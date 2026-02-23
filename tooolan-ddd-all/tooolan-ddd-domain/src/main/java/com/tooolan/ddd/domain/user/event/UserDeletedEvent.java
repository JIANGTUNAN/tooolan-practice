package com.tooolan.ddd.domain.user.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 用户删除领域事件
 * 当用户成功删除时发布此事件
 *
 * @author tooolan
 * @since 2026年2月19日
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public class UserDeletedEvent {

    /**
     * 事件源
     */
    private final Object source = this;

    /**
     * 被删除的用户ID列表
     */
    private final List<Integer> userIds;

    /**
     * 业务数据（携带删除用户的请求参数）
     * 用于日志记录，在 app 层进行 JSON 序列化
     */
    private final Object businessData;

}
