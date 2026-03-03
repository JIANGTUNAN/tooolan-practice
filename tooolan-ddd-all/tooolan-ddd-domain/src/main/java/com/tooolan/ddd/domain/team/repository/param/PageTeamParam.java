package com.tooolan.ddd.domain.team.repository.param;

import com.tooolan.ddd.domain.common.param.PageQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 分页查询小组信息 查询条件
 *
 * @author tooolan
 * @since 2026年2月24日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageTeamParam extends PageQueryParam {

    /**
     * 小组名称（模糊查询）
     */
    private String teamName;

    /**
     * 小组编码（精确查询）
     */
    private String teamCode;

    /**
     * 小组状态列表（范围查询）
     */
    private List<Integer> statusList;

    /**
     * 创建时间-开始（范围查询）
     */
    private LocalDateTime createdAtStart;

    /**
     * 创建时间-结束（范围查询）
     */
    private LocalDateTime createdAtEnd;

}
