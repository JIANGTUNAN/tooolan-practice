package com.tooolan.ddd.api.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Mock Token 常量
 * 开发环境下可通过 test-{userId} 格式的 token 模拟任意用户身份
 *
 * @author tooolan
 * @since 2026年3月31日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockTokenConstant {

    /**
     * Mock token 前缀，完整格式：MOCK_PREFIX + userId
     * 例如：test-1 表示以 userId=1 的用户身份登录
     */
    public static final String MOCK_PREFIX = "test-";

}
