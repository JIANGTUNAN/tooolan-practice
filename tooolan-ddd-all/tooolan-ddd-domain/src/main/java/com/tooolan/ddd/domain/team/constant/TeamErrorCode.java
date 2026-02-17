package com.tooolan.ddd.domain.team.constant;

import com.tooolan.ddd.domain.common.constant.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 小组模块错误码枚举
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TeamErrorCode implements ErrorCode {

    /**
     * 小组不可用
     */
    UNAVAILABLE("小组不可用，无法添加用户"),

    /**
     * 小组已满员
     */
    FULL("小组已满员"),

    /**
     * 小组不存在
     */
    NOT_FOUND("小组不存在");

    private final String message;

}
