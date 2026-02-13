package com.tooolan.ddd.domain.team.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * 小组状态枚举
 * 定义小组的三种状态：正常、停用、满员
 *
 * @author tooolan
 * @since 2026年2月13日
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TeamStatusEnum {

    /**
     * 正常状态
     */
    NORMAL(0, "正常"),

    /**
     * 停用状态
     */
    DISABLED(1, "停用"),

    /**
     * 满员状态
     */
    FULL(2, "满员");


    /**
     * 枚举值（对应数据库存储值）
     */
    private final Integer value;

    /**
     * 状态描述
     */
    private final String desc;


    /**
     * 根据值获取枚举
     *
     * @param value 状态值
     * @return 对应的枚举值，不存在时返回空
     */
    public static Optional<TeamStatusEnum> fromValue(Integer value) {
        for (TeamStatusEnum status : values()) {
            if (status.value.equals(value)) {
                return Optional.of(status);
            }
        }
        return Optional.empty();
    }

    /**
     * 判断是否可用（正常状态可用）
     */
    public boolean isAvailable() {
        return this == NORMAL;
    }

}
