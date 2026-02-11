package com.tooolan.ddd.app.team.vo;

import lombok.Value;

/**
 * 小组视图对象
 * 用于返回小组数据，包含小组基本信息和关联的部门、用户统计信息
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Value
public class TeamVo {

    /**
     * 小组ID
     */
    Integer teamId;

    /**
     * 小组名称
     */
    String teamName;

    /**
     * 小组编码
     */
    String teamCode;

    /**
     * 所属部门ID
     */
    Integer deptId;

    /**
     * 部门名称
     */
    String deptName;

    /**
     * 用户数量
     */
    Long userCount;

    /**
     * 备注
     */
    String remark;

    /**
     * 创建人
     */
    String createdBy;

    /**
     * 创建时间
     */
    String createdAt;

    /**
     * 更新人
     */
    String updatedBy;

    /**
     * 更新时间
     */
    String updatedAt;
}
