package com.tooolan.ddd.app.dept.request;

import lombok.Data;

/**
 * 更新部门 BO
 * 用于应用层接收部门更新请求
 *
 * @author tooolan
 * @since 2026年4月3日
 */
@Data
public class UpdateDeptBo {

    /**
     * 部门ID（必填）
     */
    private Integer deptId;

    /**
     * 部门名称（可选）
     */
    private String deptName;

    /**
     * 部门编码（可选）
     */
    private String deptCode;

    /**
     * 父部门ID（可选）
     */
    private Integer parentId;

    /**
     * 备注信息（可选）
     */
    private String remark;

}
