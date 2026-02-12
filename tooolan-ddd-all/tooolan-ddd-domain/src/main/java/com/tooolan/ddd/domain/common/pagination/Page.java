package com.tooolan.ddd.domain.common.pagination;

import lombok.Value;

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
     * 创建分页结果对象（5参数版本）
     *
     * @param records  数据列表
     * @param total    总记录数
     * @param pageNum  当前页码
     * @param pageSize 每页大小
     * @param pages    总页数
     * @return 分页结果对象
     */
    public static <T> Page<T> of(List<T> records, long total, int pageNum, int pageSize, int pages) {
        return new Page<>(records, total, pageNum, pageSize, pages);
    }

}
