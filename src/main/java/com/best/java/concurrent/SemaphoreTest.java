package com.best.java.concurrent;

import java.util.concurrent.Semaphore;

/**
 * @Author: xjxu3
 * @Date: 2020/1/17 14:29
 * @Description: 信号量测试
 * 使用场景： 10个人到银行排队，只有2个窗口。商场厕所等等，资源有限的情况
 */
public class SemaphoreTest implements Runnable {

	private Semaphore semaphore;

	SemaphoreTest(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		//使用信号量限流
		try {
			// 尝试获取许可成功
			if (semaphore.tryAcquire()) {
				System.out.println(Thread.currentThread().getName() + " 获得许可");
				//业务操作
				Thread.sleep(10 * 1000);
				System.out.println(Thread.currentThread().getName() + " end,释放许可");
				semaphore.release();
			} else {
				System.out.println(Thread.currentThread().getName() + " 降级处理：抛弃");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		//一般用法
//		try {
//			System.out.println(Thread.currentThread().getName() + " start");
//			// 从信号量中获取许可
//			semaphore.acquire();
//			System.out.println(Thread.currentThread().getName() + " 获得许可");
//			Thread.sleep(10 * 1000);
//			System.out.println(Thread.currentThread().getName() + " end,释放许可");
//			semaphore.release();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public static void main(String[] args) {
		// 信号量，初始数量是2个,也就是共享资源总共只有2个,先进先出公平模式
		Semaphore semaphore = new Semaphore(2,true);
		for (int i = 0; i < 5; i++) {
			new Thread(new SemaphoreTest(semaphore), "Thread-" + i).start();
		}
	}
}
