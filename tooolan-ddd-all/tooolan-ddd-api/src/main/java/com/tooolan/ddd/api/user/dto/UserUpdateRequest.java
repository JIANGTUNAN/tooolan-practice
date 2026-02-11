package com.tooolan.ddd.api.user.dto;

import com.tooolan.ddd.app.user.bo.UserUpdateBO;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户更新请求 DTO
 * 继承 UserUpdateBO 并通过重写 getter 方法添加字段校验
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserUpdateRequest extends UserUpdateBO {

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
}
