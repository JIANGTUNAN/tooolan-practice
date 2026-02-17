package com.tooolan.ddd.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tooolan.ddd.api.common.constant.ResponseCode;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVo<T> {

    /**
     * 系统状态码（20200、20404、20500 等）
     */
    private Integer code;

    /**
     * 业务状态标识（仅在异常时出现）
     */
    private String statusCode;

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
        return new ResultVo<>(ResponseCode.SUCCESS, null, "操作成功", null);
    }

    /**
     * 创建成功响应（带数据）
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return 成功响应对象
     */
    public static <T> ResultVo<T> success(T data) {
        return new ResultVo<>(ResponseCode.SUCCESS, null, "操作成功", data);
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
        return new ResultVo<>(ResponseCode.SUCCESS, null, message, data);
    }

    /**
     * 创建错误响应
     *
     * @param code       系统状态码
     * @param statusCode 业务状态标识
     * @param message    错误消息
     * @param <T>        数据类型
     * @return 错误响应对象
     */
    public static <T> ResultVo<T> error(Integer code, String statusCode, String message) {
        return new ResultVo<>(code, statusCode, message, null);
    }

    /**
     * 创建错误响应（带数据）
     *
     * @param code       系统状态码
     * @param statusCode 业务状态标识
     * @param message    错误消息
     * @param data       响应数据
     * @param <T>        数据类型
     * @return 错误响应对象
     */
    public static <T> ResultVo<T> error(Integer code, String statusCode, String message, T data) {
        return new ResultVo<>(code, statusCode, message, data);
    }

    /**
     * 创建错误响应（默认系统错误码）
     *
     * @param statusCode 业务状态标识
     * @param message    错误消息
     * @param <T>        数据类型
     * @return 错误响应对象
     */
    public static <T> ResultVo<T> error(String statusCode, String message) {
        return new ResultVo<>(ResponseCode.INTERNAL_ERROR, statusCode, message, null);
    }

}
