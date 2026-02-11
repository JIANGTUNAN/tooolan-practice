package com.tooolan.ddd.domain.common.pagination;

import lombok.Value;

import java.util.Collections;
import java.util.List;

/**
 * 分页结果对象
 * 封装分页查询结果
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Value
public class Page<T> {

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
     * 创建分页结果对象
     *
     * @param records  数据列表
     * @param total    总记录数
     * @param pageNum  当前页码
     * @param pageSize 每页大小
     * @return 分页结果对象
     */
    public static <T> Page<T> of(List<T> records, long total, int pageNum, int pageSize) {
        int pages = (int) Math.ceil((double) total / pageSize);
        return new Page<>(records, total, pageNum, pageSize, pages);
    }

    /**
     * 创建空的分页结果对象
     *
     * @param pageNum  当前页码
     * @param pageSize 每页大小
     * @return 空的分页结果对象
     */
    public static <T> Page<T> empty(int pageNum, int pageSize) {
        return new Page<>(Collections.emptyList(), 0, pageNum, pageSize, 0);
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

    /**
     * 判断是否为第一页
     *
     * @return 是否为第一页
     */
    public boolean isFirst() {
        return pageNum == 1;
    }

    /**
     * 判断是否为最后一页
     *
     * @return 是否为最后一页
     */
    public boolean isLast() {
        return pageNum >= pages;
    }

}
