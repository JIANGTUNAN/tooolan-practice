package com.tooolan.ddd.domain.common;

import com.tooolan.ddd.domain.common.exception.BusinessRuleException;
import com.tooolan.ddd.domain.common.identifier.Identifier;
import lombok.Getter;
import lombok.Setter;

/**
 * 聚合根 抽象基类
 * 所有聚合根的统一抽象，提供ID和基本字段的通用实现
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Getter
public abstract class AggregateRoot<ID extends Identifier> {

    /**
     * 聚合根ID
     */
    protected ID id;

    /**
     * 备注
     */
    @Setter
    protected String remark;

    /**
     * 验证聚合根的业务规则
     * 子类需实现具体的验证逻辑
     *
     * @throws BusinessRuleException 业务规则验证失败时抛出
     */
    public abstract void validate();

    /**
     * 判断是否为新创建的聚合根（未持久化）
     *
     * @return 是否为新对象
     */
    public boolean isNew() {
        return id == null;
    }

    /**
     * 设置聚合根ID
     * 此方法仅供基础设施层在持久化后回设ID使用
     *
     * @param id 聚合根ID
     */
    protected void setId(ID id) {
        this.id = id;
    }

}
