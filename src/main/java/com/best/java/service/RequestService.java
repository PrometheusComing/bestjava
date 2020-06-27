package com.best.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
		logger.error("it is hello");
//		for (int i = 0; i < 10; i++) {
//			int finalI = i;
//			Thread thread = new Thread(new MDCTaskRunnable(new Runnable() {
//				@Override
//				public void run() {
//					// 这里的final其实是个语法糖，局部变量表里不会有final，只在检查时有用
//					logger.info(Thread.currentThread().getName() + finalI);
//				}
//			}));
//			// submit可以通过Future的get()来catch运行异常，execute是个void
//			threadPoolExecutor.submit(thread);
//		}
		System.out.println(100/0);
		return "hello";
	}
}
