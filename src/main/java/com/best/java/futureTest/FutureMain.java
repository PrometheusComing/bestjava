package com.best.java.futureTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureMain {
	public static void main(String...args) throws InterruptedException,ExecutionException{
		FutureTask<String> futureTask = new FutureTask<>(new RealData("a-"));
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		executorService.submit(futureTask);
		System.out.println("请求完毕");
		Thread.sleep(1000);
		System.out.println("数据是： " + futureTask.get());
	}
}
