package com.tooolan.ddd.app.team.convert;

import com.tooolan.ddd.app.common.response.PageVo;
import com.tooolan.ddd.app.team.request.PageTeamBo;
import com.tooolan.ddd.app.team.request.SaveTeamBo;
import com.tooolan.ddd.app.team.request.UpdateTeamBo;
import com.tooolan.ddd.app.team.response.TeamVo;
import com.tooolan.ddd.domain.common.result.PageQueryResult;
import com.tooolan.ddd.domain.team.constant.TeamErrorCode;
import com.tooolan.ddd.domain.team.enums.TeamStatusEnum;
import com.tooolan.ddd.domain.team.exception.TeamException;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.repository.param.PageTeamParam;

/**
 * 小组转换器
 * 负责跨层对象转换
 *
 * @author tooolan
 * @since 2026年2月24日
 */
public class TeamConvert {

    /**
     * 将领域模型转换为视图对象
     * deptName 和 userCount 暂时设置为 null
     *
     * @param team 领域模型
     * @return 视图对象
     */
    public static TeamVo toVo(Team team) {
        if (team == null) {
            return null;
        }
        TeamVo vo = new TeamVo();
        vo.setTeamId(team.getId());
        vo.setTeamName(team.getTeamName());
        vo.setTeamCode(team.getTeamCode());
        vo.setDeptId(team.getDeptId());
        vo.setDeptName(null);
        vo.setUserCount(null);
        vo.setRemark(team.getRemark());
        return vo;
    }

    /**
     * 将保存小组 BO 转换为领域模型
     * 默认状态设置为正常
     *
     * @param bo 保存小组 BO
     * @return 领域模型
     */
    public static Team toDomain(SaveTeamBo bo) {
        if (bo == null) {
            return null;
        }
        Team team = new Team();
        team.setDeptId(bo.getDeptId());
        team.setTeamName(bo.getTeamName());
        team.setTeamCode(bo.getTeamCode());
        team.setMaxMembers(bo.getMaxMembers());
        team.setRemark(bo.getRemark());
        team.setStatus(TeamStatusEnum.NORMAL);
        return team;
    }

    /**
     * 将更新小组 BO 转换为领域模型（部分更新）
     * 只设置传入的非 null 字段，null 字段不会被更新
     *
     * @param bo 更新小组 BO
     * @return 领域模型
     */
    public static Team toUpdateDomain(UpdateTeamBo bo) {
        Team team = new Team();
        team.setId(bo.getTeamId());
        team.setDeptId(bo.getDeptId());
        team.setTeamName(bo.getTeamName());
        team.setMaxMembers(bo.getMaxMembers());
        team.setRemark(bo.getRemark());
        if (bo.getStatus() != null) {
            team.setStatus(TeamStatusEnum.fromValue(bo.getStatus())
                    .orElseThrow(() -> new TeamException(TeamErrorCode.STATUS_CONFLICT)));
        }
        return team;
    }

    /**
     * 将 BO 转换为 Param
     *
     * @param bo BO 对象
     * @return Param 对象
     */
    public static PageTeamParam toParam(PageTeamBo bo) {
        if (bo == null) {
            return null;
        }
        PageTeamParam param = new PageTeamParam();
        param.setPageNum(bo.getPageNum());
        param.setPageSize(bo.getPageSize());
        param.setTeamName(bo.getTeamName());
        param.setTeamCode(bo.getTeamCode());
        param.setStatusList(bo.getStatusList());
        param.setCreatedAtStart(bo.getCreatedAtStart());
        param.setCreatedAtEnd(bo.getCreatedAtEnd());
        return param;
    }

    /**
     * 将分页查询结果转换为分页视图对象
     *
     * @param result 分页查询结果
     * @return 分页视图对象
     */
    public static PageVo<TeamVo> toPageVo(PageQueryResult<Team> result) {
        return PageVo.of(result, TeamConvert::toVo);
    }

}
