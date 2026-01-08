package com.tooolan.practice.ddd.domain.model.valueobject;

import java.io.Serializable;

/**
 * 值对象基类
 * 值对象通过属性值来标识相等性，而非ID
 *
 * @author tooolan
 */
public abstract class ValueObject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 值对象没有ID，通过所有字段判断相等性
     * 子类需要实现equals和hashCode方法
     */

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

}
