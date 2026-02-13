package com.tooolan.ddd.domain.team.repository;

import com.tooolan.ddd.domain.team.model.Team;

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

}
