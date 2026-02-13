package com.tooolan.ddd.domain.common.result;

import lombok.Data;

/**
 * 分页请求对象
 * 封装分页查询参数
 *
 * @author tooolan
 * @since 2026年2月11日
 */
@Data
public abstract class PageRequest {

    /**
     * 当前页码数
     */
    protected int pageNum;

    /**
     * 显示条目数
     */
    protected int pageSize;

}
