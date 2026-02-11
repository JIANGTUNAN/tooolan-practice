package com.tooolan.ddd.api.user.dto;

import com.tooolan.ddd.app.user.bo.UserCreateBO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户创建请求 DTO
 * 继承 UserCreateBO 并通过重写 getter 方法添加字段校验
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserCreateRequest extends UserCreateBO {

    /**
     * 获取用户名，带非空校验
     *
     * @return 用户名
     */
    @Override
    @NotBlank(message = "用户名不能为空")
    public String getUsername() {
        return super.getUsername();
    }

    /**
     * 获取用户昵称，带非空校验
     *
     * @return 用户昵称
     */
    @Override
    @NotBlank(message = "用户昵称不能为空")
    public String getNickName() {
        return super.getNickName();
    }

    /**
     * 获取邮箱，带格式校验
     *
     * @return 邮箱地址
     */
    @Override
    @Email(message = "邮箱格式不正确")
    public String getEmail() {
        return super.getEmail();
    }

    /**
     * 获取所属小组ID，带非空校验
     *
     * @return 小组ID
     */
    @Override
    @NotNull(message = "所属小组不能为空")
    public Integer getTeamId() {
        return super.getTeamId();
    }
}
