package com.tooolan.practice.ddd.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tooolan.practice.ddd.domain.repository.BaseRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 仓储实现基类
 * 基于MyBatis-Plus实现通用数据访问
 *
 * @param <T>  领域对象类型
 * @param <PO> 持久化对象类型
 * @param <ID> ID类型
 * @author tooolan
 */
public abstract class BaseRepositoryImpl<T, PO, ID extends Serializable> implements BaseRepository<T, ID> {

    /**
     * 获取具体的Mapper实例
     *
     * @return Mapper实例
     */
    protected abstract BaseMapper<PO> getMapper();

    /**
     * 领域对象转PO
     *
     * @param entity 领域对象
     * @return PO对象
     */
    protected abstract PO toPO(T entity);

    /**
     * PO转领域对象
     *
     * @param po PO对象
     * @return 领域对象
     */
    protected abstract T toDomain(PO po);

    @Override
    public T save(T entity) {
        PO po = toPO(entity);
        getMapper().insert(po);
        return toDomain(po);
    }

    @Override
    public T update(T entity) {
        PO po = toPO(entity);
        getMapper().updateById(po);
        return toDomain(po);
    }

    @Override
    public void deleteById(ID id) {
        getMapper().deleteById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        PO po = getMapper().selectById(id);
        return Optional.ofNullable(po).map(this::toDomain);
    }

    @Override
    public List<T> findAll() {
        List<PO> pos = getMapper().selectList(null);
        return pos.stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(ID id) {
        return getMapper().selectById(id) != null;
    }

}
