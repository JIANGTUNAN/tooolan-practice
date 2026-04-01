package com.tooolan.ddd.app.team.request;

import lombok.Data;

/**
 * 更新小组 BO
 * 用于应用层接收小组更新请求
 *
 * @author tooolan
 * @since 2026年4月1日
 */
@Data
public class UpdateTeamBo {

    /**
     * 小组ID（必填）
     */
    private Integer teamId;

    /**
     * 所属部门ID（可选）
     */
    private Integer deptId;

    /**
     * 小组名称（可选）
     */
    private String teamName;

    /**
     * 小组状态（可选，只允许 0-正常 和 1-停用）
     */
    private Integer status;

    /**
     * 小组人数上限（可选）
     */
    private Integer maxMembers;

    /**
     * 备注信息（可选）
     */
    private String remark;

}
