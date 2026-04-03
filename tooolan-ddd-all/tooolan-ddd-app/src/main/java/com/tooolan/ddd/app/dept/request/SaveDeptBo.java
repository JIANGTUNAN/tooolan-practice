package com.tooolan.ddd.app.dept.request;

import lombok.Data;

/**
 * 保存部门 BO
 * 用于应用层接收部门保存请求
 *
 * @author tooolan
 * @since 2026年4月3日
 */
@Data
public class SaveDeptBo {

    /**
     * 部门名称（必填）
     */
    private String deptName;

    /**
     * 部门编码（必填）
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
