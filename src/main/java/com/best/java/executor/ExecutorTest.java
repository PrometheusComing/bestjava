package com.best.java.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: xjxu3
 * @Date: 2020/2/25 16:15
 * @Description:
 */
public class ExecutorTest {
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(8);
	}
}
