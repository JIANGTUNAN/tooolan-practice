package com.tooolan.ddd.app.user.bo;

import lombok.Data;

/**
 * 用户创建业务对象
 * 定义创建用户所需的业务字段
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public class UserCreateBO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 所属小组ID
     */
    private Integer teamId;

    /**
     * 备注
     */
    private String remark;
}
