package com.tooolan.ddd.domain.user.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户 实体
 * 纯数据模型，不含业务方法
 *
 * @author tooolan
 * @since 2026年2月12日
 */
@Data
@EqualsAndHashCode
public class User {

    /**
     * 用户ID
     */
    private Integer id;

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

}
