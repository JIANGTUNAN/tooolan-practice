package com.tooolan.ddd.domain.user.model;

import lombok.Value;

import java.util.Objects;

/**
 * 用户名 值对象
 * 封装用户名格式校验逻辑，确保用户名的有效性
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Value
public class Username {

    /**
     * 用户名长度最小值
     */
    private static final int MIN_LENGTH = 3;

    /**
     * 用户名长度最大值
     */
    private static final int MAX_LENGTH = 20;

    /**
     * 用户名值
     */
    String value;

    /**
     * 创建用户名值对象
     *
     * @param value 用户名字符串
     * @return 用户名值对象
     * @throws IllegalArgumentException 用户名格式无效时抛出
     */
    public static Username of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        String trimmed = value.trim();
        if (trimmed.length() < MIN_LENGTH || trimmed.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("用户名长度必须在%d-%d个字符之间", MIN_LENGTH, MAX_LENGTH));
        }
        if (!trimmed.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("用户名只能包含字母、数字和下划线");
        }
        return new Username(trimmed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Username username = (Username) o;
        return Objects.equals(value, username.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
