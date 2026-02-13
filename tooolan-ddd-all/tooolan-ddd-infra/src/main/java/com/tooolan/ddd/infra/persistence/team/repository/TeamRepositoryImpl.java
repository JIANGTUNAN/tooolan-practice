package com.tooolan.ddd.infra.persistence.team.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.repository.TeamRepository;
import com.tooolan.ddd.infra.persistence.team.converter.TeamConverter;
import com.tooolan.ddd.infra.persistence.team.entity.SysTeamEntity;
import com.tooolan.ddd.infra.persistence.team.mapper.SysTeamMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 小组 仓储实现
 * 实现小组领域层定义的仓储接口，提供数据持久化能力
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Repository
public class TeamRepositoryImpl extends ServiceImpl<SysTeamMapper, SysTeamEntity> implements TeamRepository {

    /**
     * 根据小组ID查询小组信息
     *
     * @param teamId 小组ID
     * @return 小组信息，不存在时返回空
     */
    @Override
    public Optional<Team> getTeam(Integer teamId) {
        return super.getOptById(teamId)
                .map(TeamConverter::toDomain);
    }

}
