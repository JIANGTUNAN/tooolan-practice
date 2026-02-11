package com.tooolan.ddd.domain.common.identifier;

import lombok.Value;

import java.util.Objects;

/**
 * 用户ID 值对象
 * 封装用户标识符，提供类型安全的ID封装和验证
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Value
public class UserId implements Identifier {

    /**
     * 用户ID值
     */
    Integer value;

    /**
     * 创建用户ID
     *
     * @param value 用户ID值
     * @return 用户ID值对象
     * @throws IllegalArgumentException ID值无效时抛出
     */
    public static UserId of(Integer value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("无效的用户ID");
        }
        return new UserId(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return Objects.equals(value, userId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
