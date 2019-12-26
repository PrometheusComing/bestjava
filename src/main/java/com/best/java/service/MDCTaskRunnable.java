package com.best.java.service;

import org.slf4j.MDC;

import java.util.Map;

/**
 * @Author: xjxu3
 * @Date: 2019/12/26 9:53
 * @Description: MDC装饰者
 */
public class MDCTaskRunnable implements Runnable {

	private final Map<String,String> map = MDC.getCopyOfContextMap();

	private final Runnable runnable;

	MDCTaskRunnable(Runnable runnable) {
		this.runnable = runnable;
	}

	@Override
	public void run() {
		if (map != null) {
			MDC.setContextMap(map);
		}
		try {
			runnable.run();
		} finally {
			// 线程池的线程复用，可能会将MDC信息带到其他代码，故而需要clear，消除mdc和当前线程的绑定
			MDC.clear();
		}
	}
}
