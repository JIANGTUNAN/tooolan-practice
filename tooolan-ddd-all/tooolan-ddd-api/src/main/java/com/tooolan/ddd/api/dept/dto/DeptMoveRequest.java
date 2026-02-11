package com.tooolan.ddd.api.dept.dto;

import com.tooolan.ddd.app.dept.bo.DeptMoveBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门移动请求 DTO
 * 继承 DeptMoveBO
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptMoveRequest extends DeptMoveBO {
}
