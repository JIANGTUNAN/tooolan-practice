package com.tooolan.ddd.app.team.bo;

import lombok.Data;

/**
 * 小组更新业务对象
 * 定义更新小组所需的业务字段
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public class TeamUpdateBO {

    /**
     * 小组名称
     */
    private String teamName;

    /**
     * 小组编码
     */
    private String teamCode;

    /**
     * 备注
     */
    private String remark;
}
