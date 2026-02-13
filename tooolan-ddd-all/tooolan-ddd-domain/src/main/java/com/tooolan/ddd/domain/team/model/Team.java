package com.tooolan.ddd.domain.team.model;

import com.tooolan.ddd.domain.team.enums.TeamStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 小组 实体
 * 纯数据模型，不含业务方法
 *
 * @author tooolan
 * @since 2026年2月12日
 */
@Data
@EqualsAndHashCode
public class Team {

    /**
     * 小组ID
     */
    private Integer id;

    /**
     * 所属部门ID
     */
    private Integer deptId;

    /**
     * 小组名称
     */
    private String teamName;

    /**
     * 小组编码
     */
    private String teamCode;

    /**
     * 小组状态
     */
    private TeamStatusEnum status;

    /**
     * 小组人数上限（0表示不限制）
     */
    private Integer maxMembers;


    /**
     * 判断小组是否可用（正常状态）
     *
     * @return 是否可用
     * @throws IllegalStateException 当 status 为 null 时抛出
     */
    public boolean isAvailable() {
        if (status == null) {
            throw new IllegalStateException("小组状态数据异常，请联系管理员");
        }
        return status.isAvailable();
    }

    /**
     * 判断小组是否满员
     *
     * @param currentMemberCount 当前成员数
     * @return 是否已满员
     * @throws IllegalStateException 当 maxMembers 为 null 时抛出
     */
    public boolean isFull(Integer currentMemberCount) {
        if (maxMembers == null) {
            throw new IllegalStateException("小组人数上限数据异常，请联系管理员");
        }
        return maxMembers > 0 && currentMemberCount >= maxMembers;
    }

    /**
     * 判断小组是否有人数限制
     *
     * @return 是否有人数限制
     * @throws IllegalStateException 当 maxMembers 为 null 时抛出
     */
    public boolean hasMemberLimit() {
        if (maxMembers == null) {
            throw new IllegalStateException("小组人数上限数据异常，请联系管理员");
        }
        return maxMembers > 0;
    }

}
