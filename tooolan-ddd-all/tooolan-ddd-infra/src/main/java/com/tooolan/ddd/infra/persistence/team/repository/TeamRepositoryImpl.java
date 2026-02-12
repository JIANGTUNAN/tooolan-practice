package com.tooolan.ddd.infra.persistence.team.repository;

import com.tooolan.ddd.domain.team.repository.TeamRepository;
import com.tooolan.ddd.infra.persistence.team.converter.TeamConverter;
import com.tooolan.ddd.infra.persistence.team.mapper.SysTeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 小组 仓储实现
 * 实现小组领域层定义的仓储接口，提供数据持久化能力
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Repository
@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamRepository {

    private final SysTeamMapper teamMapper;
    private final TeamConverter teamConverter;

}
