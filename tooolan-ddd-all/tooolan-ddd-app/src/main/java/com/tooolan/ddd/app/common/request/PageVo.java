package com.tooolan.ddd.app.common.request;

import lombok.Data;

import java.util.List;

/**
 * 分页视图对象
 * 统一封装分页查询的响应结果，用于应用层返回分页数据
 *
 * @param <T> 数据类型
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public class PageVo<T> {

    /**
     * 当前页码数
     */
    private long pageNum;

    /**
     * 显示条目数
     */
    private long pageSize;

    /**
     * 总页码数
     */
    private long pages;

    /**
     * 总条目数
     */
    private long total;

    /**
     * 数据列表
     */
    private List<T> records;

}
