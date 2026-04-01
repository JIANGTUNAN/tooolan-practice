package com.tooolan.ddd.domain.team.constant;

import com.tooolan.ddd.domain.common.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 小组模块错误码枚举
 * 模块编码：003
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TeamErrorCode implements ErrorCode {

    /**
     * 小组不存在
     */
    NOT_FOUND("1-003-404-001", "小组不存在"),

    /**
     * 小组编码已存在
     */
    CODE_EXISTS("1-003-409-002", "小组编码已存在"),

    /**
     * 小组不可用
     */
    UNAVAILABLE("1-003-400-003", "小组不可用，无法添加用户"),

    /**
     * 小组已满员
     */
    FULL("1-003-400-004", "小组已满员"),

    /**
     * 保存小组失败
     */
    SAVE_FAILED("1-003-500-005", "保存小组失败，请稍后再试"),

    /**
     * 更新小组失败
     */
    UPDATE_FAILED("1-003-500-006", "更新小组失败，请稍后再试"),

    /**
     * 小组内仍有成员，无法停用
     */
    HAS_MEMBERS("1-003-400-007", "小组内仍有成员，无法停用"),

    /**
     * 小组状态变更冲突
     */
    STATUS_CONFLICT("1-003-400-008", "小组状态变更冲突，请刷新后重试");

    private final String code;
    private final String message;

}
