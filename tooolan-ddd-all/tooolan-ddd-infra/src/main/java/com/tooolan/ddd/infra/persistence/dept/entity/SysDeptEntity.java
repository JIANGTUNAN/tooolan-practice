package com.tooolan.ddd.infra.persistence.dept.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tooolan.ddd.infra.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统部门信息表 实体类
 *
 * @author tooolan
 * @since 2026年2月10日
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_dept")
public class SysDeptEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 部门名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 部门编码，唯一
     */
    @TableField(value = "code")
    private String code;

    /**
     * 父部门ID，支持层级
     */
    @TableField(value = "parent_id")
    private Long parentId;

}
