package com.tooolan.ddd.app.team.bo;

import lombok.Data;

/**
 * 小组查询业务对象
 * 定义查询小组列表所需的业务字段
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public class TeamQueryBO {

    /**
     * 小组名称（模糊查询）
     */
    private String teamName;

    /**
     * 小组编码（模糊查询）
     */
    private String teamCode;

    /**
     * 所属部门ID
     */
    private Integer deptId;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页大小
     */
    private Integer pageSize;
}
