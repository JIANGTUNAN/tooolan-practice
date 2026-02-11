package com.tooolan.ddd.infra.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 逻辑删除状态枚举
 * 用于替代 Boolean 类型的魔法值，提高代码可读性和可维护性
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum DeletedStatusEnum {

    NORMAL(false, "正常"),
    DELETED(true, "已删除");


    /**
     * 枚举值
     */
    private final Boolean value;
    /**
     * 状态描述
     */
    private final String desc;

}
