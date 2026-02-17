package com.tooolan.ddd.infra.common.config;

import com.tooolan.ddd.domain.common.context.ContextHolder;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 异步任务配置
 * 提供系统任务线程池，预初始化系统上下文
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Configuration
public class AsyncTaskConfig {

    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 4;

    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 8;

    /**
     * 空闲线程存活时间（秒）
     */
    private static final long KEEP_ALIVE_TIME = 60L;

    /**
     * 任务队列容量
     */
    private static final int QUEUE_CAPACITY = 100;

    /**
     * 系统任务线程池
     * <p>
     * 预初始化系统上下文（userId=-1, username=system, nickname=系统任务），
     * 用于定时任务、消息队列消费者等非用户触发的场景。
     */
    @Bean("systemTaskExecutor")
    public ExecutorService systemTaskExecutor() {
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(QUEUE_CAPACITY),
                new SystemThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        ) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                // 执行前初始化系统上下文
                ContextHolder.initSystemContext();
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                // 执行后清理上下文
                ContextHolder.clearContext();
            }
        };
    }

    /**
     * 系统任务线程工厂
     */
    private static class SystemThreadFactory implements ThreadFactory {

        private final AtomicInteger counter = new AtomicInteger(0);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread t = new Thread(r, "system-task-" + counter.incrementAndGet());
            t.setDaemon(true);
            return t;
        }
    }

}
