package com.hours22.system_monitor_ver11.async;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@Configuration
@EnableAsync
public class SpringAsyncConfig {

    @Bean(name = "threadPoolPcOffExecutor")
    public Executor threadPoolPcOffExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(30);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setThreadNamePrefix("ExecutorPcOff-");
        taskExecutor.initialize();
        return taskExecutor;
    }
    
    @Bean(name = "threadPoolPcMsgExecutor")
    public Executor threadPoolPcMsgExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(30);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setThreadNamePrefix("ExecutorPcMsg-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean(name = "threadPoolMobileOffExecutor")
    public Executor threadPoolMobileExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(30);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setThreadNamePrefix("ExecutorMobileOff-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}