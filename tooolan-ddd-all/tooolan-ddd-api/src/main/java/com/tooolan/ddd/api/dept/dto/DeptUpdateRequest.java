package com.tooolan.ddd.api.dept.dto;

import com.tooolan.ddd.app.dept.bo.DeptUpdateBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门更新请求 DTO
 * 继承 DeptUpdateBO
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptUpdateRequest extends DeptUpdateBO {
}
