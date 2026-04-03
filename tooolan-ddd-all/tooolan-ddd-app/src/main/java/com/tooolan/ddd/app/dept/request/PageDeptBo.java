package com.tooolan.ddd.app.dept.request;

import com.tooolan.ddd.app.common.request.PageQueryBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页查询部门信息 业务类
 *
 * @author tooolan
 * @since 2026年4月3日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageDeptBo extends PageQueryBo {

    /**
     * 部门名称（模糊查询）
     */
    private String deptName;

    /**
     * 部门编码（精确查询）
     */
    private String deptCode;

    /**
     * 父部门ID
     */
    private Integer parentId;

}
