package com.tooolan.ddd.app.team.convert;

import com.tooolan.ddd.app.common.response.PageVo;
import com.tooolan.ddd.app.team.request.PageTeamBo;
import com.tooolan.ddd.app.team.request.SaveTeamBo;
import com.tooolan.ddd.app.team.request.UpdateTeamBo;
import com.tooolan.ddd.app.team.response.TeamVo;
import com.tooolan.ddd.domain.common.result.PageQueryResult;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.repository.param.PageTeamParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * 小组应用层转换器
 * 负责 BO ↔ 领域模型 ↔ VO 的转换
 *
 * @author tooolan
 * @since 2026年2月24日
 */
@Mapper(componentModel = "spring")
public interface TeamAppConverter {

    /**
     * 将领域模型转换为视图对象
     *
     * @param team 领域模型
     * @return 视图对象
     */
    @Mapping(target = "deptName", ignore = true)
    @Mapping(target = "userCount", ignore = true)
    TeamVo toVo(Team team);

    /**
     * 将领域模型列表转换为视图对象列表
     *
     * @param teams 领域模型列表
     * @return 视图对象列表
     */
    List<TeamVo> toVoList(List<Team> teams);

    /**
     * 将保存小组业务类转换为领域模型
     *
     * @param bo 保存小组业务类
     * @return 领域模型
     */
    @Mapping(target = "teamId", ignore = true)
    @Mapping(target = "status", ignore = true)
    Team toSaveDomain(SaveTeamBo bo);

    /**
     * 将更新小组业务类转换为领域模型
     *
     * @param bo 更新小组业务类
     * @return 领域模型
     */
    Team toUpdateDomain(UpdateTeamBo bo);

    /**
     * 将分页小组业务类转换为查询参数类
     *
     * @param bo 分页小组业务类
     * @return 查询参数类
     */
    PageTeamParam toParam(PageTeamBo bo);

    /**
     * 将分页查询结果转换为分页视图对象
     *
     * @param result 分页查询结果
     * @return 分页视图对象
     */
    default PageVo<TeamVo> toPageVo(PageQueryResult<Team> result) {
        return PageVo.of(result, this::toVo);
    }

}
