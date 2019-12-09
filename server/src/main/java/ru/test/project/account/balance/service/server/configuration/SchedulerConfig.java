package ru.test.project.account.balance.service.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * Configuration for scheduler
 */
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {

    /**
     * Pool size of scheduler tasks
     */
    @Value("${scheduler.pool-size}")
    private int POOL_SIZE;

    /**
     * Configure scheduler tasks
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("scheduled-task-pool-");
        threadPoolTaskScheduler.afterPropertiesSet();
        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }
}
