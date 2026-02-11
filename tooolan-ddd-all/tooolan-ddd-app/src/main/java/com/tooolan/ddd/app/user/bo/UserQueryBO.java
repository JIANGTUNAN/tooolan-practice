package com.tooolan.ddd.app.user.bo;

import lombok.Data;

/**
 * 用户查询业务对象
 * 定义查询用户列表所需的业务字段
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public class UserQueryBO {

    /**
     * 用户名（模糊查询）
     */
    private String username;

    /**
     * 用户昵称（模糊查询）
     */
    private String nickName;

    /**
     * 邮箱（模糊查询）
     */
    private String email;

    /**
     * 所属小组ID
     */
    private Integer teamId;

    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
}
