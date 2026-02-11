package com.tooolan.ddd.app.team.service;

import com.tooolan.ddd.app.common.PageVo;
import com.tooolan.ddd.app.team.bo.*;
import com.tooolan.ddd.app.team.vo.TeamVo;
import com.tooolan.ddd.domain.common.exception.NotFoundException;
import com.tooolan.ddd.domain.common.identifier.DeptId;
import com.tooolan.ddd.domain.common.identifier.TeamId;
import com.tooolan.ddd.domain.common.pagination.Page;
import com.tooolan.ddd.domain.common.pagination.PageRequest;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.model.TeamCode;
import com.tooolan.ddd.domain.team.repository.TeamRepository;
import com.tooolan.ddd.domain.team.service.TeamDomainService;
import com.tooolan.ddd.domain.user.repository.UserRepository;
import com.tooolan.ddd.app.team.assembler.TeamAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final UserRepository userRepository;
    private final TeamAssembler teamAssembler;

    /**
     * 创建小组
     *
     * @param bo 小组创建业务对象
     * @return 小组视图对象
     */
    @Transactional(rollbackFor = Exception.class)
    public TeamVo createTeam(TeamCreateBO bo) {
        // 创建小组领域对象
        Team team = teamAssembler.toDomain(bo);

        // 校验小组编码唯一性
        teamDomainService.validateTeamCodeUnique(team.getTeamCode(), null);

        // 验证业务规则
        team.validate();

        // 保存小组
        teamRepository.save(team);

        return teamAssembler.toVo(team);
    }

    /**
     * 更新小组信息
     *
     * @param teamId 小组ID
     * @param bo     小组更新业务对象
     * @return 小组视图对象
     */
    @Transactional(rollbackFor = Exception.class)
    public TeamVo updateTeam(Integer teamId, TeamUpdateBO bo) {
        TeamId id = TeamId.of(teamId);

        // 查询小组
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("小组", teamId));

        // 准备更新数据
        String teamName = bo.getTeamName() != null ? bo.getTeamName() : team.getTeamName();
        TeamCode teamCode = bo.getTeamCode() != null ? TeamCode.of(bo.getTeamCode()) : team.getTeamCode();

        // 如果修改了编码，需要校验唯一性
        if (bo.getTeamCode() != null && !bo.getTeamCode().equals(team.getTeamCode().getValue())) {
            teamDomainService.validateTeamCodeUnique(teamCode, id);
        }

        // 更新小组信息
        team.updateInfo(teamName, teamCode);

        if (bo.getRemark() != null) {
            team.setRemark(bo.getRemark());
        }

        // 验证业务规则
        team.validate();

        // 保存小组
        teamRepository.save(team);

        // 查询用户数量
        long userCount = userRepository.countByTeam(id);

        return teamAssembler.toVo(team, userCount);
    }

    /**
     * 删除小组
     *
     * @param teamId 小组ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteTeam(Integer teamId) {
        TeamId id = TeamId.of(teamId);

        // 检查小组是否存在
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("小组", teamId));

        // 检查是否有关联用户
        long userCount = userRepository.countByTeam(id);
        if (userCount > 0) {
            throw new IllegalStateException("小组下还有用户，无法删除");
        }

        // 删除小组
        teamRepository.remove(id);
    }

    /**
     * 查询小组详情
     *
     * @param teamId 小组ID
     * @return 小组视图对象
     */
    public TeamVo getTeamById(Integer teamId) {
        TeamId id = TeamId.of(teamId);
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("小组", teamId));

        // 查询用户数量
        long userCount = userRepository.countByTeam(id);

        return teamAssembler.toVo(team, userCount);
    }

    /**
     * 分页查询小组列表
     *
     * @param bo 查询业务对象
     * @return 分页视图对象
     */
    public PageVo<TeamVo> getTeamPage(TeamQueryBO bo) {
        // 构建分页请求
        PageRequest pageRequest = PageRequest.of(
                bo.getPageNum(),
                bo.getPageSize()
        );

        // 查询所有小组（简化实现，生产环境应在仓储层实现分页查询）
        List<Team> allTeams = teamRepository.findAll();

        // 筛选条件
        List<Team> filteredTeams = allTeams.stream()
                .filter(team -> {
                    boolean match = true;
                    if (bo.getTeamName() != null) {
                        match = match && team.getTeamName().contains(bo.getTeamName());
                    }
                    if (bo.getTeamCode() != null) {
                        match = match && team.getTeamCode().getValue().contains(bo.getTeamCode());
                    }
                    if (bo.getDeptId() != null) {
                        match = match && team.getDeptId() != null && team.getDeptId().getValue().equals(bo.getDeptId());
                    }
                    return match;
                })
                .toList();

        // 内存分页（简化实现）
        int total = filteredTeams.size();
        int start = pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), total);

        List<Team> pagedTeams = start < total ? filteredTeams.subList(start, end) : List.of();

        Page<Team> page = Page.of(pagedTeams, total, pageRequest.getPageNum(), pageRequest.getPageSize());

        return teamAssembler.toPageVo(page);
    }

    /**
     * 小组转移部门
     *
     * @param teamId 小组ID
     * @param bo     转移业务对象
     */
    @Transactional(rollbackFor = Exception.class)
    public void transferTeam(Integer teamId, TeamTransferBO bo) {
        TeamId id = TeamId.of(teamId);
        DeptId targetDeptId = DeptId.of(bo.getTargetDeptId());

        // 校验转移合法性
        teamDomainService.validateTeamTransfer(id, targetDeptId);

        // 查询小组
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("小组", teamId));

        // 执行转移
        team.transferTo(targetDeptId);

        // 保存小组
        teamRepository.save(team);
    }

    /**
     * 根据部门查询小组
     *
     * @param deptId 部门ID
     * @return 小组视图对象列表
     */
    public List<TeamVo> getTeamsByDept(Integer deptId) {
        DeptId id = DeptId.of(deptId);
        List<Team> teams = teamRepository.findByDept(id);

        // 查询每个小组的用户数量
        return teams.stream()
                .map(team -> {
                    long userCount = userRepository.countByTeam(team.getId());
                    return teamAssembler.toVo(team, userCount);
                })
                .toList();
    }
}
