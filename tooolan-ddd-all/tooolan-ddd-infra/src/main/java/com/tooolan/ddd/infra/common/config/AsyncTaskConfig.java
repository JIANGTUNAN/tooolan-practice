package com.tooolan.ddd.infra.common.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步任务配置
 * 提供系统任务线程池，支持 TTL 上下文传递
 *
 * @author tooolan
 * @since 2026年2月17日
 */
@Slf4j
@Configuration
@EnableAsync
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
    private static final int KEEP_ALIVE_SECONDS = 60;

    /**
     * 任务队列容量
     */
    private static final int QUEUE_CAPACITY = 100;

    /**
     * 线程名称前缀
     */
    private static final String THREAD_NAME_PREFIX = "DDD-Async-";

    /**
     * 系统任务线程池
     * <p>
     * 使用 ThreadPoolTaskExecutor 配合 TTL 包装，支持 TransmittableThreadLocal 上下文传递，
     * 用于异步任务执行（如日志记录、事件处理等）。
     *
     * @return 包装了 TTL 的线程池执行器
     */
    @Primary
    @Bean("systemTaskExecutor")
    public Executor systemTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.setAllowCoreThreadTimeOut(true);
        executor.setRejectedExecutionHandler(new SmartRejectedExecutionHandler());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);

        executor.initialize();

        // 使用 TTL 包装，支持 TransmittableThreadLocal 值传递到子线程
        return TtlExecutors.getTtlExecutor(executor.getThreadPoolExecutor());
    }

    /**
     * 智能拒绝执行处理器
     * 当线程池饱和时，提供多级降级策略
     */
    private static class SmartRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            String taskName = r.getClass().getSimpleName();
            if (!executor.isShutdown()) {
                // 策略: 在调用线程中执行（提供背压）
                log.warn("线程池已满，任务[{}]将在调用线程中执行 - 活跃线程数: {}, 队列大小: {}",
                        taskName, executor.getActiveCount(), executor.getQueue().size());
                try {
                    r.run();
                } catch (Exception e) {
                    log.error("调用线程执行任务[{}]失败", taskName, e);
                    throw new RuntimeException("任务执行失败: " + taskName, e);
                }
            } else {
                log.error("线程池已关闭，任务[{}]被拒绝执行", taskName);
                throw new RuntimeException("线程池已关闭，无法执行任务: " + taskName);
            }
        }
    }

}
