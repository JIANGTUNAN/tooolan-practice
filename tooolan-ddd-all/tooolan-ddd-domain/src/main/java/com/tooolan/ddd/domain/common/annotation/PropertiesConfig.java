package com.tooolan.ddd.domain.common.annotation;

import java.lang.annotation.*;

/**
 * DDD 配置注解
 * 用于标记领域层中的配置 POJO 类，实现自动配置绑定
 *
 * @author tooolan
 * @since 2026年3月3日
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PropertiesConfig {

    /**
     * 配置前缀
     * 对应 application.yml 中的配置路径
     *
     * @return 配置前缀
     */
    String prefix();

}
