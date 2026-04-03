package com.tooolan.ddd.domain.dept.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 部门删除领域事件
 * 当部门成功删除时发布此事件
 *
 * @author tooolan
 * @since 2026年4月3日
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public class DeptDeletedEvent {

    /**
     * 事件源
     */
    private final Object source = this;

    /**
     * 被删除的部门ID列表
     */
    private final List<Integer> deptIds;

}
