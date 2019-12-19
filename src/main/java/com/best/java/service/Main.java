package com.best.java.service;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {
	public static void main (String...args) throws InterruptedException{
//		LinkedBlockingDeque<PCData> linkedBlockingDeque = new LinkedBlockingDeque<>(10);
//		Produce produce1 = new Produce(linkedBlockingDeque);
//		Produce produce2 = new Produce(linkedBlockingDeque);
//		Produce produce3 = new Produce(linkedBlockingDeque);
//		Consumer consumer1 = new Consumer(linkedBlockingDeque);
//		Consumer consumer2 = new Consumer(linkedBlockingDeque);
//		Consumer consumer3 = new Consumer(linkedBlockingDeque);
//		new Thread(produce1).start();
//		new Thread(produce2).start();
//		new Thread(produce3).start();
//		new Thread(consumer1).start();
//		new Thread(consumer2).start();
//		new Thread(consumer3).start();
//		ExecutorService executorService = Executors.newCachedThreadPool();
//		executorService.execute(produce1);
//		executorService.execute(produce2);
//		executorService.execute(produce3);
//		executorService.execute(consumer1);
//		executorService.execute(consumer2);
//		executorService.execute(consumer3);
//		Thread.sleep(10 * 1000);
//		produce1.stop();
//		produce2.stop();
//		produce3.stop();
//		Thread.sleep(3000);
//		consumer1.stop();
//		consumer2.stop();
//		consumer3.stop();
//		executorService.shutdownNow();
//		for (;;) {
//			Thread.sleep(1000);
//			System.out.println("alive :" + Thread.activeCount());
//		}
	}
}
