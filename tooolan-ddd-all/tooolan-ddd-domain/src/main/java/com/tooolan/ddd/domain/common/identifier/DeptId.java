package com.tooolan.ddd.domain.common.identifier;

import lombok.Value;

import java.util.Objects;

/**
 * 部门ID 值对象
 * 封装部门标识符，提供类型安全的ID封装和验证
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Value
public class DeptId implements Identifier {

    /**
     * 部门ID值
     */
    Integer value;

    /**
     * 创建部门ID
     *
     * @param value 部门ID值
     * @return 部门ID值对象
     * @throws IllegalArgumentException ID值无效时抛出
     */
    public static DeptId of(Integer value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("无效的部门ID");
        }
        return new DeptId(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeptId deptId = (DeptId) o;
        return Objects.equals(value, deptId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
