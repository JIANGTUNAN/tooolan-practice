package com.tooolan.ddd.domain.user.model;

import cn.hutool.core.lang.Validator;
import lombok.Value;

import java.util.Objects;

/**
 * 邮箱 值对象
 * 封装邮箱格式校验逻辑，确保邮箱地址的有效性
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Value
public class Email {

    /**
     * 邮箱地址值
     */
    String value;

    /**
     * 创建邮箱值对象
     *
     * @param value 邮箱地址字符串
     * @return 邮箱值对象
     * @throws IllegalArgumentException 邮箱格式无效时抛出
     */
    public static Email of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("邮箱不能为空");
        }
        if (!Validator.isEmail(value)) {
            throw new IllegalArgumentException("无效邮箱格式");
        }
        return new Email(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
