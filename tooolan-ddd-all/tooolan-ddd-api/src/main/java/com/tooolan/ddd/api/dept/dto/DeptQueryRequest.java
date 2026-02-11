package com.tooolan.ddd.api.dept.dto;

import com.tooolan.ddd.app.dept.bo.DeptQueryBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门查询请求 DTO
 * 继承 DeptQueryBO，用于查询部门列表
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptQueryRequest extends DeptQueryBO {
}
