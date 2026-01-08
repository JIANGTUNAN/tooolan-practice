package com.tooolan.practice.ddd.domain.model.aggregate;

import com.tooolan.practice.ddd.domain.event.DomainEvent;
import com.tooolan.practice.ddd.domain.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 聚合根基类
 * 聚合根是领域模型的入口，负责维护聚合内部的一致性边界
 *
 * @author tooolan
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AggregateRoot extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 领域事件列表
     */
    private List<DomainEvent> domainEvents = new ArrayList<>();

    /**
     * 添加领域事件
     *
     * @param event 领域事件
     */
    protected void addDomainEvent(DomainEvent event) {
        if (event != null) {
            this.domainEvents.add(event);
        }
    }

    /**
     * 获取并清空领域事件
     *
     * @return 领域事件列表
     */
    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = this.domainEvents;
        this.domainEvents = new ArrayList<>();
        return events;
    }

    /**
     * 清空领域事件
     */
    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

}
