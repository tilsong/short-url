package com.toy.shorturl.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class SpringAsyncConfig {

	@Bean(name = "threadPoolTaskExecutor")
	public Executor threadPoolTaskExecutor(){
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

		// thread 개수 선정을 위한 변수들
		int maxRunnableProcessor = Runtime.getRuntime().availableProcessors();
		int idleTime = 45;
		int serviceTime = 60;

		taskExecutor.setCorePoolSize(maxRunnableProcessor/2);
		taskExecutor.setMaxPoolSize(maxRunnableProcessor * (1+idleTime)/serviceTime);
		taskExecutor.setQueueCapacity(100);
		taskExecutor.setThreadNamePrefix("Executor-");

		return taskExecutor;
	}
}
