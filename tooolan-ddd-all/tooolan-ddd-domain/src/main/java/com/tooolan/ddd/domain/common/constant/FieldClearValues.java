package com.tooolan.ddd.domain.common.constant;

import cn.hutool.core.util.ObjUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 字段清空值常量类
 * 定义用于清空可选字段的约定值
 *
 * @author tooolan
 * @since 2026年2月14日
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldClearValues {

    /**
     * 字符串字段清空约定值
     */
    public static final String CLEAR_VALUE = "_clear";

    /**
     * 整型字段清空约定值
     */
    public static final Integer INTEGER_CLEAR_VALUE = -1;


    /**
     * 处理字符串字段清空
     *
     * @param value 字符串字段值
     * @return 处理后的值，如果是约定值则返回 null，否则返回原值
     */
    public static String processField(String value) {
        return ObjUtil.equal(CLEAR_VALUE, value) ? null : value;
    }

    /**
     * 处理整数字段清空
     *
     * @param value 整数字段值
     * @return 处理后的值，如果是约定值则返回 null，否则返回原值
     */
    public static Integer processField(Integer value) {
        return ObjUtil.equal(INTEGER_CLEAR_VALUE, value) ? null : value;
    }

}
