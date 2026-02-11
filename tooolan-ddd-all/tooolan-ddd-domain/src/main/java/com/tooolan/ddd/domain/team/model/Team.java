package com.tooolan.ddd.domain.team.model;

import com.tooolan.ddd.domain.common.AggregateRoot;
import com.tooolan.ddd.domain.common.exception.BusinessRuleException;
import com.tooolan.ddd.domain.common.identifier.DeptId;
import com.tooolan.ddd.domain.common.identifier.TeamId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 小组 聚合根
 * 封装小组的核心业务逻辑，包括小组创建、信息更新、部门转移等行为
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Team extends AggregateRoot<TeamId> {

    /**
     * 所属部门ID
     */
    private DeptId deptId;

    /**
     * 小组名称
     */
    private String teamName;

    /**
     * 小组编码
     */
    private TeamCode teamCode;

    /**
     * 创建小组
     *
     * @param deptId  所属部门ID
     * @param teamName 小组名称
     * @param teamCode 小组编码
     * @return 小组聚合根
     */
    public static Team create(DeptId deptId, String teamName, TeamCode teamCode) {
        Team team = new Team();
        team.deptId = deptId;
        team.teamName = teamName;
        team.teamCode = teamCode;
        return team;
    }

    /**
     * 从持久化数据恢复小组对象
     *
     * @param teamId   小组ID
     * @param deptId   所属部门ID
     * @param teamName 小组名称
     * @param teamCode 小组编码
     * @param remark   备注
     * @return 小组聚合根
     */
    public static Team restore(TeamId teamId, DeptId deptId, String teamName,
                               TeamCode teamCode, String remark) {
        Team team = new Team();
        team.id = teamId;
        team.deptId = deptId;
        team.teamName = teamName;
        team.teamCode = teamCode;
        team.remark = remark;
        return team;
    }

    /**
     * 更新小组基本信息
     *
     * @param teamName 小组名称
     * @param teamCode 小组编码
     */
    public void updateInfo(String teamName, TeamCode teamCode) {
        this.teamName = teamName;
        this.teamCode = teamCode;
    }

    /**
     * 转移小组到新部门
     *
     * @param newDeptId 目标部门ID
     * @throws IllegalArgumentException 目标部门ID为空时抛出
     */
    public void transferTo(DeptId newDeptId) {
        if (newDeptId == null) {
            throw new IllegalArgumentException("目标部门ID不能为空");
        }
        this.deptId = newDeptId;
    }

    @Override
    public void validate() {
        if (deptId == null) {
            throw new BusinessRuleException("所属部门不能为空");
        }
        if (teamCode == null) {
            throw new BusinessRuleException("小组编码不能为空");
        }
    }

    /**
     * 分配ID
     * 此方法仅供基础设施层在持久化后回设ID使用
     *
     * @param teamId 小组ID
     */
    public void assignId(TeamId teamId) {
        this.id = teamId;
    }
}
