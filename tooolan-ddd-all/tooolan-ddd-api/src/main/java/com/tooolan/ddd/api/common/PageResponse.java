package com.tooolan.ddd.api.common;

import lombok.Value;

import java.util.List;

/**
 * 分页响应包装类
 * 统一封装分页查询的响应结果
 *
 * @param <T> 数据类型
 * @author tooolan
 * @since 2026年2月11日
 */
@Value
public class PageResponse<T> {

    /**
     * 数据列表
     */
    List<T> records;

    /**
     * 总记录数
     */
    long total;

    /**
     * 当前页码
     */
    int pageNum;

    /**
     * 每页大小
     */
    int pageSize;

    /**
     * 总页数
     */
    int pages;

    /**
     * 创建分页响应对象
     *
     * @param records  数据列表
     * @param total    总记录数
     * @param pageNum  当前页码
     * @param pageSize 每页大小
     * @param <T>      数据类型
     * @return 分页响应对象
     */
    public static <T> PageResponse<T> of(List<T> records, long total, int pageNum, int pageSize) {
        int pages = (int) Math.ceil((double) total / pageSize);
        return new PageResponse<>(records, total, pageNum, pageSize, pages);
    }

    /**
     * 判断是否有下一页
     *
     * @return 是否有下一页
     */
    public boolean hasNext() {
        return pageNum < pages;
    }

    /**
     * 判断是否有上一页
     *
     * @return 是否有上一页
     */
    public boolean hasPrevious() {
        return pageNum > 1;
    }
}
