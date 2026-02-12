package com.tooolan.ddd.api.dept;

import com.tooolan.ddd.app.dept.service.DeptApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部门管理控制器
 * 提供部门相关的 REST API，包括部门的增删改查和移动操作
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@RestController
@RequestMapping("/api/v1/depts")
@RequiredArgsConstructor
public class DeptController {

    private final DeptApplicationService deptApplicationService;

}
