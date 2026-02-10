package com.tooolan.ddd.infra.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus 配置类
 * 配置分页插件和 Mapper 扫描
 *
 * @author tooolan
 * @since 2026年2月10日
 */
@Configuration
@MapperScan("com.tooolan.ddd.infra.persistence.*.mapper")
public class MyBatisPlusConfig {

    /**
     * 配置 MyBatis Plus 拦截器
     * 添加分页插件，支持 MySQL 数据库的分页查询
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
