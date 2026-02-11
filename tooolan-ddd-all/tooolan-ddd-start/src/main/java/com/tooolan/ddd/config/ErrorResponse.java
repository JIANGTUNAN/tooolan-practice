package com.tooolan.ddd.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * 错误响应对象
 * 统一封装API错误响应格式
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    /**
     * HTTP状态码
     */
    private int status;

    /**
     * 错误代码
     */
    private String code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 错误详情（可选）
     */
    private String details;

    /**
     * 请求路径（可选）
     */
    private String path;

    /**
     * 时间戳
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private ZonedDateTime timestamp;

    /**
     * 创建错误响应
     *
     * @param status  HTTP状态码
     * @param code    错误代码
     * @param message 错误消息
     * @return 错误响应
     */
    public static ErrorResponse of(int status, String code, String message) {
        return ErrorResponse.builder()
                .status(status)
                .code(code)
                .message(message)
                .timestamp(ZonedDateTime.now(ZoneId.of("UTC")))
                .build();
    }

    /**
     * 创建带详情的错误响应
     *
     * @param status  HTTP状态码
     * @param code    错误代码
     * @param message 错误消息
     * @param details 错误详情
     * @return 错误响应
     */
    public static ErrorResponse of(int status, String code, String message, String details) {
        return ErrorResponse.builder()
                .status(status)
                .code(code)
                .message(message)
                .details(details)
                .timestamp(ZonedDateTime.now(ZoneId.of("UTC")))
                .build();
    }

    /**
     * 创建带路径的错误响应
     *
     * @param status  HTTP状态码
     * @param code    错误代码
     * @param message 错误消息
     * @param path    请求路径
     * @return 错误响应
     */
    public static ErrorResponse of(int status, String code, String message, String path, String details) {
        return ErrorResponse.builder()
                .status(status)
                .code(code)
                .message(message)
                .path(path)
                .details(details)
                .timestamp(ZonedDateTime.now(ZoneId.of("UTC")))
                .build();
    }
}
