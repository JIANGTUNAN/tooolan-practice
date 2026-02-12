package com.tooolan.ddd.domain.user.service;

import com.tooolan.ddd.domain.common.annotation.DomainService;
import lombok.RequiredArgsConstructor;

/**
 * 用户 领域服务（原子服务）
 * 暂时为空，后续有不可拆分的业务逻辑时再添加
 * 例如：用户迁移、批量操作等
 *
 * @author tooolan
 * @since 2026年2月12日
 */
@DomainService
@RequiredArgsConstructor
public class UserDomainService {
    // 暂时为空，后续添加原子化的业务逻辑
    // 例如：
    // - validateUserTransfer(Integer userId, Integer teamId) // 校验用户是否可迁移
    // - batchTransferUsers(List<Integer> userIds, Integer teamId) // 批量迁移用户
}
