package com.tooolan.ddd.domain.team.repository;

import com.tooolan.ddd.domain.common.param.PageQueryResult;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.repository.param.PageTeamParam;

import java.util.Optional;

/**
 * 小组 仓储接口
 * 定义小组持久化操作契约，由基础设施层实现
 *
 * @author tooolan
 * @since 2026年2月11日
 */
public interface TeamRepository {

    /**
     * 根据小组ID查询小组信息
     *
     * @param teamId 小组ID
     * @return 小组信息，不存在时返回空
     */
    Optional<Team> getTeam(Integer teamId);

    /**
     * 分页查询小组信息
     * 支持按小组名称模糊查询
     * 支持按小组编码精确查询
     * 支持按小组状态范围查询
     * 支持按创建时间范围筛选
     *
     * @param pageTeamParam 分页查询参数
     * @return 分页查询结果
     */
    PageQueryResult<Team> pageTeam(PageTeamParam pageTeamParam);

}
