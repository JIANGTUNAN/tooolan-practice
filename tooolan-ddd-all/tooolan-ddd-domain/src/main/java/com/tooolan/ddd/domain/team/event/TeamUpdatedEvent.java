package com.tooolan.ddd.domain.team.event;

import com.tooolan.ddd.domain.team.model.Team;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 小组更新事件
 * 用于通知应用层记录操作日志
 *
 * @author tooolan
 * @since 2026年4月1日
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public class TeamUpdatedEvent {

    /**
     * 事件源对象
     */
    private final Object source = this;

    /**
     * 更新后的小组
     */
    private final Team team;

    /**
     * 业务数据（用于日志记录）
     */
    private final Object businessData;

}
