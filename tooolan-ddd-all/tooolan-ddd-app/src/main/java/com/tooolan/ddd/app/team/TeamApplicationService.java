package com.tooolan.ddd.app.team;

import com.tooolan.ddd.app.common.request.PageVo;
import com.tooolan.ddd.app.common.response.OptionVo;
import com.tooolan.ddd.app.team.convert.TeamConvert;
import com.tooolan.ddd.app.team.request.PageTeamBo;
import com.tooolan.ddd.app.team.response.TeamVo;
import com.tooolan.ddd.domain.common.param.PageQueryResult;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.repository.TeamRepository;
import com.tooolan.ddd.domain.team.repository.param.PageTeamParam;
import com.tooolan.ddd.domain.team.service.TeamDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 小组应用服务
 * 提供小组相关的业务编排和事务管理
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Service
@RequiredArgsConstructor
public class TeamApplicationService {

    private final TeamRepository teamRepository;
    private final TeamDomainService teamDomainService;


    /**
     * 获取小组选项列表
     *
     * @param teamName 小组名称（可选，模糊匹配）
     * @return 小组选项列表
     */
    public OptionVo<Integer> getTeamOptions(String teamName) {
        List<Team> teams = teamRepository.listTeamOptions(teamName);
        return OptionVo.from(teams, Team::getId, Team::getTeamName);
    }

    /**
     * 根据小组ID获取小组信息
     *
     * @param teamId 小组ID
     * @return 小组视图对象
     */
    public Optional<TeamVo> getTeamById(Integer teamId) {
        Optional<Team> team = teamRepository.getTeam(teamId);
        return team.map(TeamConvert::toVo);
    }

    /**
     * 分页查询小组信息
     *
     * @param bo 查询条件
     * @return 分页结果
     */
    public PageVo<TeamVo> pageTeam(PageTeamBo bo) {
        PageTeamParam pageTeamParam = TeamConvert.toParam(bo);
        PageQueryResult<Team> pageQueryResult = teamRepository.pageTeam(pageTeamParam);
        return TeamConvert.toPageVo(pageQueryResult);
    }

}
