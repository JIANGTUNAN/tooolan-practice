package com.tooolan.ddd.domain.common.param;

import lombok.Data;

import java.util.List;

/**
 * 分页结果对象
 * 封装分页查询结果
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public class PageQueryResult<T> {

    /**
     * 总记录数
     */
    long total;

    /**
     * 当前页码
     */
    long pageNum;

    /**
     * 每页大小
     */
    long pageSize;

    /**
     * 总页数
     */
    long pages;

    /**
     * 数据列表
     */
    List<T> records;

}
