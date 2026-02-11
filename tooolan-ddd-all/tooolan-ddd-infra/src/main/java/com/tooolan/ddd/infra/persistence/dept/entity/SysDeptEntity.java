package com.tooolan.ddd.infra.persistence.dept.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tooolan.ddd.infra.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统部门信息 实体类
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_dept")
public class SysDeptEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "dept_id", type = IdType.AUTO)
    private Integer deptId;

    /**
     * 部门名称
     */
    @TableField(value = "dept_name")
    private String deptName;

    /**
     * 部门编码，唯一
     */
    @TableField(value = "dept_code")
    private String deptCode;

    /**
     * 父部门ID，支持层级
     */
    @TableField(value = "parent_id")
    private Integer parentId;

}
