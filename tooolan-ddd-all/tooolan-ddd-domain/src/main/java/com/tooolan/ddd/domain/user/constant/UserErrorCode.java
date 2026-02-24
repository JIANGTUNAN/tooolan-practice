package com.tooolan.ddd.domain.user.constant;

import com.tooolan.ddd.domain.common.constant.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户模块错误码枚举
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserErrorCode implements ErrorCode {

    /**
     * 用户名已存在
     */
    USERNAME_EXISTS("用户名已存在"),

    /**
     * 用户名不可修改
     */
    USERNAME_IMMUTABLE("用户名不允许修改"),

    /**
     * 用户不存在
     */
    NOT_FOUND("用户不存在"),

    /**
     * 保存用户失败
     */
    SAVE_FAILED("保存用户失败，请联系管理员"),

    /**
     * 更新用户失败
     */
    UPDATE_FAILED("更新用户失败，请联系管理员"),

    /**
     * 删除用户失败
     */
    DELETE_FAILED("删除用户失败，请联系管理员"),

    /**
     * 不能删除管理员账户
     */
    CANNOT_DELETE_ADMIN("不能删除管理员账户"),

    /**
     * 原密码错误
     */
    OLD_PASSWORD_MISMATCH("原密码错误，请重新输入"),

    /**
     * 新旧密码相同
     */
    PASSWORD_SAME_AS_OLD("新密码不能与原密码相同"),

    /**
     * 修改密码失败
     */
    PASSWORD_CHANGE_FAILED("修改密码失败，请联系管理员");

    private final String message;

}
