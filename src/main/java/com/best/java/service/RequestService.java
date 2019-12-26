package com.best.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Line;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: xjxu3
 * @Date: 2019/12/10 1:29
 * @Description:
 */
@Service
public class RequestService {
	private final static Logger logger = LoggerFactory.getLogger(RequestService.class);

	static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)Executors.newCachedThreadPool();

	public String getHello() {
		logger.info("it is hello");
		for (int i = 0; i < 10; i++) {
			int finalI = i;
			Thread thread = new Thread(new MDCTaskRunnable(new Runnable() {
				@Override
				public void run() {
					// jdk8里，看似finalI没有被final修饰，其实编译后会自动加上
					logger.info(Thread.currentThread().getName() + finalI);
				}
			}));
			// submit可以哦那个过Future的get()来catch运行异常，execute是个void
			threadPoolExecutor.submit(thread);
		}
		return "hello";
	}
}
