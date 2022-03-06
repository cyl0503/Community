package com.cyl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author CYL
 */
//@EnableScheduling 启动定时任务

@Configuration
@EnableScheduling
@EnableAsync
public class ThreadPoolConfig {
}

