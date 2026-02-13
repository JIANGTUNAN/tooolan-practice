package com.tooolan.ddd.app.common.response;

import lombok.Data;

/**
 * 分页查询基类
 * 所有分页查询 BO 对象的父类
 *
 * @author tooolan
 * @since 2026年2月12日
 */
@Data
public abstract class PageQueryBo {

    /**
     * 当前页码数
     */
    protected Integer pageNum;

    /**
     * 显示条目数
     */
    protected Integer pageSize;

}
