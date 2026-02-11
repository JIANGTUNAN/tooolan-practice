package com.tooolan.ddd.domain.common.identifier;

import lombok.Value;

import java.util.Objects;

/**
 * 小组ID 值对象
 * 封装小组标识符，提供类型安全的ID封装和验证
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Value
public class TeamId implements Identifier {

    /**
     * 小组ID值
     */
    Integer value;

    /**
     * 创建小组ID
     *
     * @param value 小组ID值
     * @return 小组ID值对象
     * @throws IllegalArgumentException ID值无效时抛出
     */
    public static TeamId of(Integer value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("无效的小组ID");
        }
        return new TeamId(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamId teamId = (TeamId) o;
        return Objects.equals(value, teamId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
