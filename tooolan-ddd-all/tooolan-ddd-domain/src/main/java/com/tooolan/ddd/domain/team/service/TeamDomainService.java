package com.tooolan.ddd.domain.team.service;

import com.tooolan.ddd.domain.common.annotation.DomainService;
import lombok.RequiredArgsConstructor;

/**
 * 小组 领域服务（原子服务）
 * 暂时为空，后续有不可拆分的业务逻辑时再添加
 * 例如：删除小组前的校验、人数统计等
 *
 * @author tooolan
 * @since 2026年2月12日
 */
@DomainService
@RequiredArgsConstructor
public class TeamDomainService {
    // 暂时为空，后续添加原子化的业务逻辑
    // 例如：
    // - validateTeamDeletion(Integer teamId) // 校验是否可删除（检查是否有用户）
    // - countTeamMembers(Integer teamId)      // 统计小组成员数
}
