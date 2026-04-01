package com.tooolan.ddd.domain.team.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 小组删除领域事件
 * 当小组成功删除时发布此事件
 *
 * @author tooolan
 * @since 2026年4月1日
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public class TeamDeletedEvent {

    /**
     * 事件源
     */
    private final Object source = this;

    /**
     * 被删除的小组ID列表
     */
    private final List<Integer> teamIds;

    /**
     * 业务数据（携带删除小组的请求参数）
     * 用于日志记录，在 app 层进行 JSON 序列化
     */
    private final Object businessData;

}
