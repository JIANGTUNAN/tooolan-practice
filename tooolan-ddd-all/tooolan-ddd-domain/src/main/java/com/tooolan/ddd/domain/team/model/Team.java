package com.tooolan.ddd.domain.team.model;

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

}
