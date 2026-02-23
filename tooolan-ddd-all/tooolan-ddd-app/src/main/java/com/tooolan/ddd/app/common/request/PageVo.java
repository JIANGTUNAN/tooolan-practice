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


    /**
     * 创建空的分页视图对象
     * 用于分页查询结果为 null 时快速构建空响应
     *
     * @param <T> 数据类型
     * @return 空的分页视图对象
     */
    public static <T> PageVo<T> empty() {
        PageVo<T> vo = new PageVo<>();
        vo.setPageNum(0);
        vo.setPageSize(0);
        vo.setPages(0);
        vo.setTotal(0);
        vo.setRecords(List.of());
        return vo;
    }

}
