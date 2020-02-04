package com.best.java.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: xjxu3
 * @Date: 2020/2/4 11:56
 * @Description: 使用场景
 * 实现最大的并行性：有时我们想同时启动多个线程，实现最大程度的并行性。例如，我们想测试一个单例类。
 * 如果我们创建一个初始计数为1的CountDownLatch，并让所有线程都在这个锁上等待，那么我们可以很轻松地完成测试。
 * 我们只需调用 一次countDown()方法就可以让所有的等待线程同时恢复执行。
 *
 * 开始执行前等待n个线程完成各自任务：例如应用程序启动类要确保在处理用户请求前，所有N个外部系统已经启动和运行了。
 *
 *
 */
public class CountDownLatchTest implements Runnable {

	private CountDownLatch countDownLatch;

	CountDownLatchTest(CountDownLatch countDownLatch){
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " start");
		Random random = new Random();
		try {
			Thread.sleep(random.nextInt(10000) + 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " finish");
		countDownLatch.countDown();
	}

	public static void main(String[] args) {
		CountDownLatch countDownLatch = new CountDownLatch(10);
		for (int i = 0; i < 10; i++) {
			new Thread(new CountDownLatchTest(countDownLatch), "Thread-" + i).start();
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("all thread finish,main can do next job");
	}
}
