package com.tooolan.ddd.app.common.response;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用选项视图对象
 * 继承 LinkedHashMap，key 为选项值（ID），value 为选项名（label）
 * - 利用 Map 特性保证 key 不重复
 * - 保持插入顺序
 * - 自定义序列化输出为 [{label, value}, ...] 格式
 *
 * @param <K> 选项值类型（通常为 Integer 或 Long）
 * @author tooolan
 * @since 2026年2月23日
 */
@NoArgsConstructor
public class OptionVo<K> extends LinkedHashMap<K, String> {

    /**
     * 静态工厂方法
     *
     * @param key   选项值（ID）
     * @param label 选项名（显示名）
     * @param <K>   选项值类型
     * @return OptionVo 对象
     */
    public static <K> OptionVo<K> of(K key, String label) {
        OptionVo<K> vo = new OptionVo<>();
        vo.addOption(key, label);
        return vo;
    }

    /**
     * 添加一个选项
     *
     * @param key   选项值（ID）
     * @param label 选项名（显示名）
     */
    public void addOption(K key, String label) {
        this.put(key, label);
    }

    /**
     * 转换为前端友好的 List 格式
     * 用于 Jackson 序列化
     *
     * @return 选项列表
     */
    @JsonValue
    public List<Map<String, Object>> toJson() {
        List<Map<String, Object>> list = new ArrayList<>();
        this.forEach((key, label) -> {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("label", label);
            item.put("value", key);
            list.add(item);
        });
        return list;
    }

}
