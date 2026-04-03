package com.tooolan.ddd.domain.dept.repository.param;

import com.tooolan.ddd.domain.common.param.PageQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页查询部门信息 查询条件
 *
 * @author tooolan
 * @since 2026年4月3日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageDeptParam extends PageQueryParam {

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
