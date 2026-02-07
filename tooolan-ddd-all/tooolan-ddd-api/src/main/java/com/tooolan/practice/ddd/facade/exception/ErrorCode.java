package com.tooolan.practice.ddd.facade.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码枚举
 *
 * @author tooolan
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * 成功
     */
    SUCCESS("00000", "成功"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR("99999", "系统错误"),

    /**
     * 参数错误
     */
    PARAM_ERROR("40000", "参数错误"),

    /**
     * 业务错误
     */
    BIZ_ERROR("50000", "业务错误"),

    /**
     * 资源不存在
     */
    NOT_FOUND("40400", "资源不存在"),

    /**
     * 数据已存在
     */
    DUPLICATE_DATA("40001", "数据已存在");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误描述
     */
    private final String desc;

}
