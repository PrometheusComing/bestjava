package com.best.java.concurrent;

import java.util.concurrent.Exchanger;

/**
 * @Author: xjxu3
 * @Date: 2020/2/4 15:35
 * @Description:
 */
public class ExchangerTest {
	// 生产者
	static class Producer implements Runnable {

		private Exchanger<String> exchanger;

		Producer(Exchanger<String> exchanger) {
			this.exchanger = exchanger;
		}

		@Override
		public void run() {
			for (int i = 1; i < 5; i++) {
				try {
					Thread.sleep(2000);
					String data = String.valueOf(i);
					System.out.println(Thread.currentThread().getName() + " 交换前:" + data);
					data = exchanger.exchange(data);
					System.out.println(Thread.currentThread().getName() + " 交换后:" + data);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 消费者
	static class Consumer implements Runnable {
		private Exchanger<String> exchanger;

		Consumer(Exchanger<String> exchanger) {
			this.exchanger = exchanger;
		}

		@Override
		public void run() {
				// 判断当前中断标志位
			    while (!Thread.currentThread().isInterrupted()) {
			    // 判断当前中断标志位,并自动恢复(true变false),静态方法
//				while (!Thread.interrupted()) {
					String data = "a";
					System.out.println(Thread.currentThread().getName() + " 交换前:" + data);
					try {
						data = exchanger.exchange(data);
						Thread.sleep(1000);
						System.out.println(Thread.currentThread().getName() + " 交换后:" + data);
					} catch (InterruptedException e) {
						System.out.println(Thread.currentThread().getName() + " 中断交换后:" + data);
						// 捕获后会将中断标志位复原为false，所以需要再次自己中断，结束
						Thread.currentThread().interrupt();
					}
				}
				System.out.println("结束执行");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Exchanger<String> exchanger = new Exchanger<String>();
		Thread threadA = new Thread(new Producer(exchanger),"Producer");
		Thread threadB = new Thread(new Consumer(exchanger),"Consumer");
		threadA.start();
		threadB.start();
		Thread.sleep(10000);
		threadB.interrupt();
	}
}
