package com.tooolan.ddd.domain.dept.event;

import com.tooolan.ddd.domain.dept.model.Dept;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 部门创建事件
 * 用于通知应用层记录操作日志
 *
 * @author tooolan
 * @since 2026年4月3日
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public class DeptCreatedEvent {

    /**
     * 事件源对象
     */
    private final Object source = this;

    /**
     * 创建的部门
     */
    private final Dept dept;

    /**
     * 业务数据（用于日志记录）
     */
    private final Object businessData;

}
