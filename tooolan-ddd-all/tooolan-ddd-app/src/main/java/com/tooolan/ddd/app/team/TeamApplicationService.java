package com.tooolan.ddd.app.team;

import cn.hutool.core.util.ObjUtil;
import com.tooolan.ddd.app.common.response.OptionVo;
import com.tooolan.ddd.app.common.response.PageVo;
import com.tooolan.ddd.app.team.convert.TeamConvert;
import com.tooolan.ddd.app.team.request.DeleteTeamBo;
import com.tooolan.ddd.app.team.request.PageTeamBo;
import com.tooolan.ddd.app.team.request.SaveTeamBo;
import com.tooolan.ddd.app.team.request.UpdateTeamBo;
import com.tooolan.ddd.app.team.response.TeamVo;
import com.tooolan.ddd.domain.common.result.PageQueryResult;
import com.tooolan.ddd.domain.dept.constant.DeptErrorCode;
import com.tooolan.ddd.domain.dept.exception.DeptException;
import com.tooolan.ddd.domain.dept.repository.DeptRepository;
import com.tooolan.ddd.domain.team.constant.TeamErrorCode;
import com.tooolan.ddd.domain.team.event.TeamCreatedEvent;
import com.tooolan.ddd.domain.team.event.TeamDeletedEvent;
import com.tooolan.ddd.domain.team.event.TeamUpdatedEvent;
import com.tooolan.ddd.domain.team.exception.TeamException;
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
     * 根据小组ID获取小组信息
     *
     * @param teamId 小组ID
     * @return 小组视图对象
     */
    public Optional<TeamVo> getById(Integer teamId) {
        Optional<Team> team = teamRepository.getById(teamId);
        return team.map(TeamConvert::toVo);
    }

    /**
     * 分页查询小组信息
     *
     * @param bo 查询条件
     * @return 分页结果
     */
    public PageVo<TeamVo> page(PageTeamBo bo) {
        PageTeamParam pageTeamParam = TeamConvert.toParam(bo);
        PageQueryResult<Team> pageQueryResult = teamRepository.page(pageTeamParam);
        return TeamConvert.toPageVo(pageQueryResult);
    }

    /**
     * 获取小组选项列表
     *
     * @param teamName 小组名称（可选，模糊匹配）
     * @return 小组选项列表
     */
    public OptionVo<Integer> getOptions(String teamName) {
        List<Team> teams = teamRepository.listOptions(teamName);
        return OptionVo.from(teams, Team::getId, Team::getTeamName);
    }

    /**
     * 保存小组
     * 包含应用层校验、领域服务调用和事件发布
     *
     * @param bo 保存小组 BO
     * @throws DeptException 指定的部门不存在时抛出
     * @throws TeamException 小组编码已存在或保存失败时抛出
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void save(SaveTeamBo bo) {
        // 转换为领域模型
        Team team = TeamConvert.toDomain(bo);
        // 应用层校验：如果指定了部门，校验部门是否存在
        if (ObjUtil.isNotNull(bo.getDeptId())) {
            if (!deptRepository.existById(bo.getDeptId())) {
                throw new DeptException(DeptErrorCode.NOT_FOUND);
            }
        }
        // 调用领域服务保存小组（主键会通过引用回填）
        teamDomainService.saveTeam(team);
        // 发布小组创建事件（携带业务数据用于日志记录）
        eventPublisher.publishEvent(TeamCreatedEvent.of(team, bo));
    }

    /**
     * 更新小组信息
     * 包含应用层校验、领域服务调用和事件发布
     *
     * @param bo 更新小组 BO
     * @throws DeptException                                       指定的部门不存在时抛出
     * @throws TeamException 小组不存在或状态变更冲突时抛出
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(UpdateTeamBo bo) {
        // 1. 查询现有小组
        Team existingTeam = teamRepository.getById(bo.getTeamId())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));

        // 2. 转换为领域模型（部分更新）
        Team updatedTeam = TeamConvert.toUpdateDomain(bo);

        // 3. 如果修改了部门，校验部门存在性
        if (ObjUtil.notEqual(existingTeam.getDeptId(), bo.getDeptId())) {
            if (!deptRepository.existById(bo.getDeptId())) {
                throw new DeptException(DeptErrorCode.NOT_FOUND);
            }
        }

        // 4. 调用领域服务更新小组（包含状态变更校验）
        teamDomainService.updateTeam(existingTeam, updatedTeam);

        // 5. 发布小组更新事件
        eventPublisher.publishEvent(TeamUpdatedEvent.of(updatedTeam, bo));
    }

    /**
     * 批量删除小组
     * 包含应用层校验、领域服务调用和事件发布
     *
     * @param bo 删除小组 BO
     * @throws TeamException 小组不存在、有成员或删除失败时抛出
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(DeleteTeamBo bo) {
        // 应用层校验：小组是否存在
        if (!teamRepository.existByIds(bo.getTeamIds())) {
            throw new TeamException(TeamErrorCode.NOT_FOUND);
        }
        // 调用领域服务删除小组
        teamDomainService.deleteTeams(bo.getTeamIds());
        // 发布小组删除事件（携带业务数据用于日志记录）
        eventPublisher.publishEvent(TeamDeletedEvent.of(bo.getTeamIds(), bo));
    }

}
