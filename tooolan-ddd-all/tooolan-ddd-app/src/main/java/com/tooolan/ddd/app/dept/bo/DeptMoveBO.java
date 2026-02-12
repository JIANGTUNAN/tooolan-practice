package com.tooolan.ddd.app.dept.bo;

import lombok.Data;

/**
 * 部门移动业务对象
 * 定义移动部门节点所需的业务字段
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public class DeptMoveBO {

    /**
     * 目标父部门ID（null 表示移动到根节点）
     */
    private Integer targetParentId;

}
