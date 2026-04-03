package com.tooolan.ddd.domain.dept.constant;

import com.tooolan.ddd.domain.common.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 部门模块错误码枚举
 * 模块编码：001
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
    NOT_FOUND("1-001-404-001", "部门不存在"),

    /**
     * 部门编码已存在
     */
    CODE_EXISTS("1-001-409-002", "部门编码已存在"),

    /**
     * 保存部门失败
     */
    SAVE_FAILED("1-001-500-003", "保存部门失败，请稍后再试"),

    /**
     * 更新部门失败
     */
    UPDATE_FAILED("1-001-500-004", "更新部门失败，请稍后再试"),

    /**
     * 删除部门失败
     */
    DELETE_FAILED("1-001-500-005", "删除部门失败，请稍后再试"),

    /**
     * 该部门下存在子部门，无法删除
     */
    HAS_CHILD_DEPT("1-001-400-006", "该部门下存在子部门，无法删除"),

    /**
     * 该部门下存在小组，无法删除
     */
    HAS_TEAM("1-001-400-007", "该部门下存在小组，无法删除"),

    /**
     * 父部门不存在
     */
    PARENT_DEPT_NOT_FOUND("1-001-404-008", "父部门不存在"),

    /**
     * 不能将自身设为父部门
     */
    CANNOT_SET_SELF_AS_PARENT("1-001-400-009", "不能将自身设为父部门");

    private final String code;
    private final String message;

}
