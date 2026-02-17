package com.tooolan.ddd.domain.dept.constant;

import com.tooolan.ddd.domain.common.constant.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 部门模块错误码枚举
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DeptErrorCode implements ErrorCode {

    /**
     * 部门不存在
     */
    NOT_FOUND("部门不存在"),

    /**
     * 部门编码已存在
     */
    CODE_EXISTS("部门编码已存在");

    private final String message;

}
