package com.tooolan.ddd.infra.persistence.team.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tooolan.ddd.domain.common.identifier.DeptId;
import com.tooolan.ddd.domain.common.identifier.TeamId;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.model.TeamCode;
import com.tooolan.ddd.domain.team.repository.TeamRepository;
import com.tooolan.ddd.infra.persistence.team.converter.TeamConverter;
import com.tooolan.ddd.infra.persistence.team.entity.SysTeamEntity;
import com.tooolan.ddd.infra.persistence.team.mapper.SysTeamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public void save(Team team) {
        SysTeamEntity entity = teamConverter.toEntity(team);

        if (team.isNew()) {
            // 新增：插入数据
            teamMapper.insert(entity);
            // 回设ID到领域对象
            if (entity.getTeamId() != null) {
                team.assignId(TeamId.of(entity.getTeamId()));
            }
        } else {
            // 更新：更新数据
            teamMapper.updateById(entity);
        }
    }

    @Override
    public void remove(TeamId teamId) {
        if (teamId != null) {
            teamMapper.deleteById(teamId.getValue());
        }
    }

    @Override
    public Optional<Team> findById(TeamId teamId) {
        if (teamId == null) {
            return Optional.empty();
        }
        SysTeamEntity entity = teamMapper.selectById(teamId.getValue());
        return Optional.ofNullable(teamConverter.toDomain(entity));
    }

    @Override
    public List<Team> findAll() {
        List<SysTeamEntity> entities = teamMapper.selectList(null);
        return entities.stream()
                .map(teamConverter::toDomain)
                .toList();
    }

    @Override
    public boolean existsByTeamCode(TeamCode teamCode) {
        if (teamCode == null) {
            return false;
        }
        LambdaQueryWrapper<SysTeamEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysTeamEntity::getTeamCode, teamCode.getValue());
        return teamMapper.selectCount(wrapper) > 0;
    }

    @Override
    public Optional<Team> findByTeamCode(TeamCode teamCode) {
        if (teamCode == null) {
            return Optional.empty();
        }
        LambdaQueryWrapper<SysTeamEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysTeamEntity::getTeamCode, teamCode.getValue());
        SysTeamEntity entity = teamMapper.selectOne(wrapper);
        return Optional.ofNullable(teamConverter.toDomain(entity));
    }

    @Override
    public List<Team> findByDept(DeptId deptId) {
        if (deptId == null) {
            return List.of();
        }
        LambdaQueryWrapper<SysTeamEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysTeamEntity::getDeptId, deptId.getValue());
        List<SysTeamEntity> entities = teamMapper.selectList(wrapper);
        return entities.stream()
                .map(teamConverter::toDomain)
                .toList();
    }

    @Override
    public boolean isTeamCodeUnique(TeamCode teamCode, TeamId excludeTeamId) {
        if (teamCode == null) {
            return false;
        }
        LambdaQueryWrapper<SysTeamEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysTeamEntity::getTeamCode, teamCode.getValue());
        if (excludeTeamId != null) {
            wrapper.ne(SysTeamEntity::getTeamId, excludeTeamId.getValue());
        }
        return teamMapper.selectCount(wrapper) == 0;
    }

    @Override
    public long countByDept(DeptId deptId) {
        if (deptId == null) {
            return 0;
        }
        LambdaQueryWrapper<SysTeamEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysTeamEntity::getDeptId, deptId.getValue());
        return teamMapper.selectCount(wrapper);
    }
}
