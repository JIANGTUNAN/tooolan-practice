package com.tooolan.ddd.infra.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.tooolan.ddd.infra.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus 自动填充处理器
 * 自动处理 createdBy/createdAt/updatedBy/updatedAt 等审计字段
 *
 * @author tooolan
 * @since 2026年2月10日
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * xxx
     * xxxxxx
     *
     * @param metaObject xxx
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        String currentUserId = RequestContext.getCurrentUserId();

        this.strictInsertFill(metaObject, BaseEntity.Fields.createdBy, String.class, currentUserId);
        this.strictInsertFill(metaObject, BaseEntity.Fields.createdAt, LocalDateTime.class, now);
        this.strictInsertFill(metaObject, BaseEntity.Fields.updatedBy, String.class, currentUserId);
        this.strictInsertFill(metaObject, BaseEntity.Fields.updatedAt, LocalDateTime.class, now);
    }

    /**
     * xxx
     * xxxxxx
     *
     * @param metaObject xxx
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        String currentUserId = RequestContext.getCurrentUserId();
        this.strictUpdateFill(metaObject, BaseEntity.Fields.updatedBy, String.class, currentUserId);
        this.strictUpdateFill(metaObject, BaseEntity.Fields.updatedAt, LocalDateTime.class, LocalDateTime.now());
    }

    /**
     * 获取当前用户ID
     */
    private String getCurrentUserId() {
        try {
            return RequestContext.getCurrentUserId();
        } catch (Exception e) {
            log.warn("获取当前用户ID失败，使用默认值: {}", e.getMessage());
            return "system";
        }
    }

}
