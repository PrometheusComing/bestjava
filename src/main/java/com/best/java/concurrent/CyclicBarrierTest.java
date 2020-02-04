package com.best.java.concurrent;

import java.util.concurrent.CyclicBarrier;

/**
 * @Author: xjxu3
 * @Date: 2020/2/4 12:16
 * @Description:
 */
public class CyclicBarrierTest implements Runnable {

	private CyclicBarrier cyclicBarrier;

	CyclicBarrierTest(CyclicBarrier cyclicBarrier) {
		this.cyclicBarrier = cyclicBarrier;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
			System.out.println(Thread.currentThread().getName() + " 到达栅栏 A");
			cyclicBarrier.await();
			System.out.println(Thread.currentThread().getName() + " 冲破栅栏 A");

			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getName() + " 到达栅栏 B");
			cyclicBarrier.await();
			System.out.println(Thread.currentThread().getName() + " 冲破栅栏 B");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// 5个线程参与，最后一个到达的线程执行最后的任务
		CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> System.out.println(Thread.currentThread().getName() + "来完成最后任务"));
		for (int i = 0; i < 5; i++) {
			new Thread(new CyclicBarrierTest(cyclicBarrier), "Thread-" + i).start();
		}
	}
}
