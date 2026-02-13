package com.tooolan.ddd.infra.persistence.team.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tooolan.ddd.infra.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统小组信息 实体类
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_team")
public class SysTeamEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "team_id", type = IdType.AUTO)
    private Integer teamId;

    /**
     * 所属部门ID
     */
    @TableField(value = "dept_id")
    private Integer deptId;

    /**
     * 小组名称
     */
    @TableField(value = "team_name")
    private String teamName;

    /**
     * 小组编码
     */
    @TableField(value = "team_code")
    private String teamCode;

    /**
     * 小组状态（0:正常,1:停用,2:满员）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 小组人数上限（0表示不限制）
     */
    @TableField(value = "max_members")
    private Integer maxMembers;

}
