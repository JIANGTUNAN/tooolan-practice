package com.tooolan.ddd.app.dept.request;

import lombok.Data;

import java.util.List;

/**
 * 批量删除部门 BO
 *
 * @author tooolan
 * @since 2026年4月3日
 */
@Data
public class DeleteDeptBo {

    /**
     * 部门ID列表
     */
    private List<Integer> deptIds;

}
