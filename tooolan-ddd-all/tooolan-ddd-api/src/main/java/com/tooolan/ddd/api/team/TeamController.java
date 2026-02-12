package com.tooolan.ddd.api.team;

import com.tooolan.ddd.app.team.service.TeamApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小组管理控制器
 * 提供小组相关的 REST API，包括小组的增删改查和转移操作
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamApplicationService teamApplicationService;

}
