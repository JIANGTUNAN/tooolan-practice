package com.tooolan.ddd.app.user.bo;

import lombok.Data;

/**
 * 用户更新业务对象
 * 定义更新用户所需的业务字段
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public class UserUpdateBO {

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 备注
     */
    private String remark;
}
