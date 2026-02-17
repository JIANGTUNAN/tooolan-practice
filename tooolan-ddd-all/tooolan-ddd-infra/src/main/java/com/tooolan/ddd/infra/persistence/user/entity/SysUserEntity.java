package com.tooolan.ddd.infra.persistence.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tooolan.ddd.infra.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户信息 实体类
 *
 * @author tooolan
 * @since 2026年2月10日
 */
@Data
@TableName(value = "sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUserEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 所属小组ID
     */
    @TableField(value = "team_id")
    private Integer teamId;

    /**
     * 用户账户
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 密码（BCrypt 加密）
     */
    @TableField(value = "password")
    private String password;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

}
