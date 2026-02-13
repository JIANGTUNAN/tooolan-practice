package com.tooolan.ddd.app.user.response;

import lombok.Data;

/**
 * 用户视图对象
 * 用于返回用户数据，包含用户基本信息和关联的小组信息
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public class UserVo {

    /**
     * 用户ID
     */
    Integer userId;

    /**
     * 用户名
     */
    String username;

    /**
     * 用户昵称
     */
    String nickName;

    /**
     * 邮箱
     */
    String email;

    /**
     * 所属小组ID
     */
    Integer teamId;

    /**
     * 小组名称
     */
    String teamName;

    /**
     * 备注
     */
    String remark;

}
