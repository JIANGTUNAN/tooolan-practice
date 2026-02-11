package com.tooolan.ddd.domain.dept.model;

import lombok.Value;

import java.util.Objects;

/**
 * 部门编码 值对象
 * 封装部门编码格式校验逻辑，确保编码的有效性
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Value
public class DeptCode {

    /**
     * 编码长度最小值
     */
    private static final int MIN_LENGTH = 2;

    /**
     * 编码长度最大值
     */
    private static final int MAX_LENGTH = 10;

    /**
     * 编码值
     */
    String value;

    /**
     * 创建部门编码值对象
     *
     * @param value 编码字符串
     * @return 部门编码值对象
     * @throws IllegalArgumentException 编码格式无效时抛出
     */
    public static DeptCode of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("部门编码不能为空");
        }
        String trimmed = value.trim().toUpperCase();
        if (trimmed.length() < MIN_LENGTH || trimmed.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("部门编码长度必须在%d-%d个字符之间", MIN_LENGTH, MAX_LENGTH));
        }
        if (!trimmed.matches("^[A-Z0-9_]+$")) {
            throw new IllegalArgumentException("部门编码只能包含大写字母、数字和下划线");
        }
        return new DeptCode(trimmed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeptCode deptCode = (DeptCode) o;
        return Objects.equals(value, deptCode.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
