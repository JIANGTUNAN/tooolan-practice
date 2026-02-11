package com.tooolan.ddd.domain.common.identifier;

/**
 * 标识符接口
 * 领域模型中所有实体ID的统一抽象，提供类型安全的标识符封装
 *
 * @author tooolan
 * @since 2026年2月11日
 */
public interface Identifier {

    /**
     * 获取标识符的值
     *
     * @return 标识符值
     */
    Object getValue();
}
