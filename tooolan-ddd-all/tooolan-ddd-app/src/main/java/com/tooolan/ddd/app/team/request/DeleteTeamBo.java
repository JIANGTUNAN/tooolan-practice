package com.tooolan.ddd.app.team.request;

import lombok.Data;

import java.util.List;

/**
 * 批量删除小组 BO
 *
 * @author tooolan
 * @since 2026年4月1日
 */
@Data
public class DeleteTeamBo {

    /**
     * 小组ID列表
     */
    private List<Integer> teamIds;

}
