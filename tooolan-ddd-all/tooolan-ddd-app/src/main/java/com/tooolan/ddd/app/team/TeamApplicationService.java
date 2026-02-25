package com.tooolan.ddd.app.team;

import cn.hutool.core.util.ObjUtil;
import com.tooolan.ddd.app.common.request.PageVo;
import com.tooolan.ddd.app.common.response.OptionVo;
import com.tooolan.ddd.app.team.convert.TeamConvert;
import com.tooolan.ddd.app.team.request.PageTeamBo;
import com.tooolan.ddd.app.team.request.SaveTeamBo;
import com.tooolan.ddd.app.team.response.TeamVo;
import com.tooolan.ddd.domain.common.exception.BusinessRuleException;
import com.tooolan.ddd.domain.common.exception.NotFoundException;
import com.tooolan.ddd.domain.common.param.PageQueryResult;
import com.tooolan.ddd.domain.dept.constant.DeptErrorCode;
import com.tooolan.ddd.domain.dept.repository.DeptRepository;
import com.tooolan.ddd.domain.team.event.TeamCreatedEvent;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.repository.TeamRepository;
import com.tooolan.ddd.domain.team.repository.param.PageTeamParam;
import com.tooolan.ddd.domain.team.service.TeamDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    private final DeptRepository deptRepository;
    private final TeamDomainService teamDomainService;
    private final ApplicationEventPublisher eventPublisher;


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

    /**
     * 保存小组
     * 包含应用层校验、领域服务调用和事件发布
     *
     * @param bo 保存小组 BO
     * @throws NotFoundException     指定的部门不存在时抛出
     * @throws BusinessRuleException 小组编码已存在或保存失败时抛出
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveTeam(SaveTeamBo bo) throws BusinessRuleException {
        // 转换为领域模型
        Team team = TeamConvert.toDomain(bo);
        // 应用层校验：如果指定了部门，校验部门是否存在
        if (ObjUtil.isNotNull(bo.getDeptId())) {
            deptRepository.getDept(bo.getDeptId())
                    .orElseThrow(() -> new NotFoundException(DeptErrorCode.NOT_FOUND));
        }
        // 调用领域服务保存小组（主键会通过引用回填）
        teamDomainService.saveTeam(team);
        // 发布小组创建事件（携带业务数据用于日志记录）
        eventPublisher.publishEvent(TeamCreatedEvent.of(team, bo));
    }

}
