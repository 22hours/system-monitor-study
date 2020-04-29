package com.hours22.system_monitor_ver11.async;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@Configuration
@EnableAsync
public class SpringAsyncConfig {
	//Common 
	@Bean(name = "threadPoolHome")
	public Executor threadPoolHomeExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(100);
        taskExecutor.setQueueCapacity(30);
        taskExecutor.setThreadNamePrefix("Home-");
        taskExecutor.initialize();
        return taskExecutor;
    }
	
	//PC
	@Bean(name = "threadPoolPcData")
	public Executor threadPoolPcDataExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(100);
        taskExecutor.setQueueCapacity(30);
        taskExecutor.setThreadNamePrefix("PcData-");
        taskExecutor.initialize();
        return taskExecutor;
    }
	
    @Bean(name = "threadPoolPcPowerOff")
    public Executor threadPoolPcOffExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(100);
        taskExecutor.setQueueCapacity(30);
        taskExecutor.setThreadNamePrefix("PcPowerOff-");
        taskExecutor.initialize();
        return taskExecutor;
    }
    
    @Bean(name = "threadPoolPCPowerOffMsg")
    public Executor threadPoolPcPowerOffMsgExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(100);
        taskExecutor.setQueueCapacity(30);
        taskExecutor.setThreadNamePrefix("PCPowerOffMsg-");
        taskExecutor.initialize();
        return taskExecutor;
    }
    
    @Bean(name = "threadPoolPCPowerOffMsgReturn")
    public Executor threadPoolPcMsgReturnExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(100);
        taskExecutor.setQueueCapacity(30);
        taskExecutor.setThreadNamePrefix("PCPowerOffMsg-Return-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    // Mobile
    @Bean(name = "threadPoolMobileGetPcs")
    public Executor threadPoolMobileGetPcsExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setThreadNamePrefix("MobilePcs-");
        taskExecutor.initialize();
        return taskExecutor;
    }
    
    @Bean(name = "threadPoolMobileGetPcData")
    public Executor threadPoolMobileGetPcDataExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(100);
        taskExecutor.setQueueCapacity(30);
        taskExecutor.setThreadNamePrefix("MobilePcData-");
        taskExecutor.initialize();
        return taskExecutor;
    }
    
    
    @Bean(name = "threadPoolMobilePowerOff")
    public Executor threadPoolMobileExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(100);
        taskExecutor.setQueueCapacity(30);
        taskExecutor.setThreadNamePrefix("MobilePowerOff-");
        taskExecutor.initialize();
        return taskExecutor;
    }
    
    @Bean(name = "threadPoolMobileLogin")
    public Executor threadPoolMobileLoginExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(100);
        taskExecutor.setQueueCapacity(30);
        taskExecutor.setThreadNamePrefix("MobileLogin-");
        taskExecutor.initialize();
        return taskExecutor;
    }
    
    @Bean(name = "threadPoolMobileClass")
    public Executor threadPoolMobileClassExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(100);
        taskExecutor.setQueueCapacity(30);
        taskExecutor.setThreadNamePrefix("MobileClass-");
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean(name = "threadPoolMobileClassId")
    public Executor threadPoolMobileClassIdExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(100);
        taskExecutor.setQueueCapacity(30);
        taskExecutor.setThreadNamePrefix("MobileClassId-");
        taskExecutor.initialize();
        return taskExecutor;
    }
    
    @Bean(name = "threadPoolMobileClassPowerOff")
    public Executor threadPoolMobileClassPowerOffExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(100);
        taskExecutor.setQueueCapacity(30);
        taskExecutor.setThreadNamePrefix("MobileClassPowerOff-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}