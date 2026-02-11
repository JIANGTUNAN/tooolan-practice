package com.tooolan.ddd.app.team.bo;

import lombok.Data;

/**
 * 小组转移业务对象
 * 定义小组转移部门所需的业务字段
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public class TeamTransferBO {

    /**
     * 目标部门ID
     */
    private Integer targetDeptId;
}
