package com.tooolan.ddd.domain.common.pagination;

import lombok.Value;

/**
 * 分页请求对象
 * 封装分页查询参数
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Value
public class PageRequest {

    /**
     * 页码（从1开始）
     */
    int pageNum;

    /**
     * 每页大小
     */
    int pageSize;

}
