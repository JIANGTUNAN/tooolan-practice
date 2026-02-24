package com.tooolan.ddd.app.team.convert;

import com.tooolan.ddd.app.common.request.PageVo;
import com.tooolan.ddd.app.team.request.PageTeamBo;
import com.tooolan.ddd.app.team.response.TeamVo;
import com.tooolan.ddd.domain.common.param.PageQueryResult;
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
        return vo;
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
