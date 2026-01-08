package com.tooolan.practice.ddd.domain.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * 仓储接口基类
 * 定义通用的数据访问方法
 *
 * @param <T>  实体类型
 * @param <ID> ID类型
 * @author tooolan
 */
public interface BaseRepository<T, ID extends Serializable> {

    /**
     * 保存实体
     *
     * @param entity 实体对象
     * @return 保存后的实体
     */
    T save(T entity);

    /**
     * 更新实体
     *
     * @param entity 实体对象
     * @return 更新后的实体
     */
    T update(T entity);

    /**
     * 根据ID删除
     *
     * @param id ID
     */
    void deleteById(ID id);

    /**
     * 根据ID查找
     *
     * @param id ID
     * @return 实体对象
     */
    Optional<T> findById(ID id);

    /**
     * 查找所有
     *
     * @return 实体列表
     */
    List<T> findAll();

    /**
     * 判断是否存在
     *
     * @param id ID
     * @return true-存在，false-不存在
     */
    boolean existsById(ID id);

}
