package com.tooolan.ddd.domain.team.repository;

import com.tooolan.ddd.domain.team.model.Team;
import com.tooolan.ddd.domain.team.model.TeamCode;
import com.tooolan.ddd.domain.common.identifier.DeptId;
import com.tooolan.ddd.domain.common.identifier.TeamId;

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
     * 保存小组
     *
     * @param team 小组聚合根
     */
    void save(Team team);

    /**
     * 删除小组
     *
     * @param teamId 小组ID
     */
    void remove(TeamId teamId);

    /**
     * 根据ID查询小组
     *
     * @param teamId 小组ID
     * @return 小组聚合根
     */
    Optional<Team> findById(TeamId teamId);

    /**
     * 查询所有小组
     *
     * @return 小组列表
     */
    List<Team> findAll();

    /**
     * 判断小组编码是否存在
     *
     * @param teamCode 小组编码
     * @return 是否存在
     */
    boolean existsByTeamCode(TeamCode teamCode);

    /**
     * 根据小组编码查询小组
     *
     * @param teamCode 小组编码
     * @return 小组聚合根
     */
    Optional<Team> findByTeamCode(TeamCode teamCode);

    /**
     * 根据部门ID查询小组列表
     *
     * @param deptId 部门ID
     * @return 小组列表
     */
    List<Team> findByDept(DeptId deptId);

    /**
     * 校验小组编码唯一性（排除指定小组）
     *
     * @param teamCode      小组编码
     * @param excludeTeamId 排除的小组ID
     * @return 是否唯一
     */
    boolean isTeamCodeUnique(TeamCode teamCode, TeamId excludeTeamId);

    /**
     * 统计部门小组数量
     *
     * @param deptId 部门ID
     * @return 小组数量
     */
    long countByDept(DeptId deptId);
}
