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
     * 默认页码
     */
    private static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 默认每页大小
     */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 最大每页大小
     */
    private static final int MAX_PAGE_SIZE = 100;

    /**
     * 页码（从1开始）
     */
    int pageNum;

    /**
     * 每页大小
     */
    int pageSize;

    /**
     * 创建分页请求对象
     *
     * @param pageNum  页码（从1开始）
     * @param pageSize 每页大小
     * @return 分页请求对象
     */
    public static PageRequest of(int pageNum, int pageSize) {
        int validPageNum = pageNum < 1 ? DEFAULT_PAGE_NUM : pageNum;
        int validPageSize = pageSize < 1 ? DEFAULT_PAGE_SIZE : Math.min(pageSize, MAX_PAGE_SIZE);
        return new PageRequest(validPageNum, validPageSize);
    }

    /**
     * 创建默认分页请求对象
     *
     * @return 分页请求对象
     */
    public static PageRequest defaultPage() {
        return new PageRequest(DEFAULT_PAGE_NUM, DEFAULT_PAGE_SIZE);
    }

    /**
     * 计算偏移量（用于数据库查询）
     *
     * @return 偏移量
     */
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }

}
