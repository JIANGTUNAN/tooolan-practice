package com.tooolan.ddd.infra.persistence.team.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tooolan.ddd.infra.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统小组信息表 实体类
 *
 * @author tooolan
 * @since 2026年2月10日
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_team")
public class SysTeamEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属部门ID
     */
    @TableField(value = "dept_id")
    private Long deptId;

    /**
     * 小组名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 小组编码
     */
    @TableField(value = "code")
    private String code;

}
