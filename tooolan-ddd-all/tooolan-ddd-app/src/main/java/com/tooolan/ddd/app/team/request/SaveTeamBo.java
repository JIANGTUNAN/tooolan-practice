package com.tooolan.ddd.app.team.request;

import lombok.Data;

/**
 * 保存小组 BO
 * 用于应用层接收小组保存请求
 *
 * @author tooolan
 * @since 2026年2月24日
 */
@Data
public class SaveTeamBo {

    /**
     * 所属部门ID（可选）
     */
    private Integer deptId;

    /**
     * 小组名称（必填）
     */
    private String teamName;

    /**
     * 小组编码（必填）
     */
    private String teamCode;

    /**
     * 小组人数上限（可选）
     */
    private Integer maxMembers;

    /**
     * 备注信息（可选）
     */
    private String remark;

}
