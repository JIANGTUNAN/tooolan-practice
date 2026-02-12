package com.tooolan.ddd.domain.common.annotation;

import java.lang.annotation.*;

/**
 * 领域服务注解
 * 通过应用层配置让 Spring 识别此注解
 *
 * @author tooolan
 * @since 2026年2月12日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DomainService {

    /**
     * 服务名称，可选
     */
    String value() default "";

}
