package com.tooolan.ddd.domain.team.repository;

import com.tooolan.ddd.domain.common.result.PageQueryResult;
import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.repository.param.PageTeamParam;

import java.util.List;
import java.util.Optional;

/**
 * 小组 仓储接口
 * 定义小组持久化操作契约，由基础设施层实现
 *
 * @author tooolan
 * @since 2026年2月11日
 */
public interface TeamRepository {

    /**
     * 查询小组选项列表
     * 用于下拉框选择，支持按小组名称模糊查询
     * 只返回正常状态的小组
     *
     * @param teamName 小组名称（可选，模糊匹配）
     * @return 小组列表（仅包含 ID 和名称）
     */
    List<Team> listTeamOptions(String teamName);

    /**
     * 根据小组ID查询小组信息
     *
     * @param teamId 小组ID
     * @return 小组信息，不存在时返回空
     */
    Optional<Team> getTeam(Integer teamId);

    /**
     * 分页查询小组信息
     *
     * @param pageTeamParam 分页查询参数
     * @return 分页查询结果
     */
    PageQueryResult<Team> pageTeam(PageTeamParam pageTeamParam);

    /**
     * 根据小组编码查询小组信息
     *
     * @param teamCode 小组编码
     * @return 小组信息，不存在时返回空
     */
    Optional<Team> getTeamByCode(String teamCode);

    /**
     * 保存小组
     * 保存成功后会回填小组ID
     *
     * @param team 小组领域模型
     * @return 是否保存成功
     */
    boolean save(Team team);

    /**
     * 根据小组ID更新小组信息
     * 仅更新非 null 字段，部分更新策略
     *
     * @param team 小组领域模型
     * @return 是否更新成功
     */
    boolean updateById(Team team);

    /**
     * 判断小组是否存在
     *
     * @param teamId 小组ID
     * @return 是否存在
     */
    boolean existById(Integer teamId);

    /**
     * 批量逻辑删除小组
     *
     * @param teamIds 小组ID列表
     * @return 删除的记录数
     */
    int deleteByIds(List<Integer> teamIds);

    /**
     * 批量判断小组是否全部存在
     *
     * @param teamIds 小组ID列表
     * @return 所有小组都存在返回 true，否则返回 false
     */
    boolean existByIds(List<Integer> teamIds);

}
