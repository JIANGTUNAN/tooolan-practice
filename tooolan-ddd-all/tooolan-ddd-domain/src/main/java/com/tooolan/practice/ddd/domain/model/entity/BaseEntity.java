package com.tooolan.practice.ddd.domain.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 领域实体基类
 * 所有领域实体继承此类，包含基础字段和通用方法
 *
 * @author tooolan
 */
@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 版本号（乐观锁）
     */
    private Integer version;

    /**
     * 判断是否为新实体
     *
     * @return true-新实体，false-已存在实体
     */
    public abstract boolean isNew();

}
