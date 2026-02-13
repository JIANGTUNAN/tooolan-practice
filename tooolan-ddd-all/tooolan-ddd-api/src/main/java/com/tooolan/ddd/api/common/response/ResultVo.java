package com.tooolan.ddd.api.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应包装类
 * 封装所有 API 接口的返回格式，包含响应码、消息和数据
 *
 * @param <T> 数据类型
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo<T> {

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 创建成功响应（无数据）
     *
     * @param <T> 数据类型
     * @return 成功响应对象
     */
    public static <T> ResultVo<T> success() {
        return new ResultVo<>(200, "操作成功", null);
    }

    /**
     * 创建成功响应（带数据）
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return 成功响应对象
     */
    public static <T> ResultVo<T> success(T data) {
        return new ResultVo<>(200, "操作成功", data);
    }

    /**
     * 创建成功响应（自定义消息）
     *
     * @param message 响应消息
     * @param data    响应数据
     * @param <T>     数据类型
     * @return 成功响应对象
     */
    public static <T> ResultVo<T> success(String message, T data) {
        return new ResultVo<>(200, message, data);
    }

    /**
     * 创建错误响应
     *
     * @param code    错误码
     * @param message 错误消息
     * @param <T>     数据类型
     * @return 错误响应对象
     */
    public static <T> ResultVo<T> error(Integer code, String message) {
        return new ResultVo<>(code, message, null);
    }

    /**
     * 创建错误响应（默认错误码）
     *
     * @param message 错误消息
     * @param <T>     数据类型
     * @return 错误响应对象
     */
    public static <T> ResultVo<T> error(String message) {
        return new ResultVo<>(500, message, null);
    }

}
