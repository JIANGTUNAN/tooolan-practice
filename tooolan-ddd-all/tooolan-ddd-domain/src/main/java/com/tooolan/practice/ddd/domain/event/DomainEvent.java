package com.tooolan.practice.ddd.domain.event;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 领域事件基类
 *
 * @author tooolan
 */
@Data
public abstract class DomainEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 事件发生时间
     */
    private LocalDateTime occurredOn;

    /**
     * 事件类型
     */
    private String eventType;

    public DomainEvent() {
        this.occurredOn = LocalDateTime.now();
        this.eventType = this.getClass().getSimpleName();
    }

}
