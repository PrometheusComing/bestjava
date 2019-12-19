package com.best.java.service;

import javax.management.relation.RelationNotFoundException;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Produce implements Runnable {

	private BlockingDeque<PCData> blockingDeque;

	private volatile boolean stopFlag = true;

	private final int SLEEP_TIME = 1000;

	private AtomicInteger atomicInteger = new AtomicInteger(0);

	public Produce(BlockingDeque<PCData> blockingDeque) {
		this.blockingDeque = blockingDeque;
	}

	@Override
	public void run() {
		PCData pcData ;
		Random random = new Random();
		System.out.println("produce id:" + Thread.currentThread().getId());
		try {
			while (stopFlag) {
				Thread.sleep(random.nextInt(SLEEP_TIME));
				pcData =  new PCData(atomicInteger.incrementAndGet());
				if (!blockingDeque.offer(pcData,2, TimeUnit.SECONDS)) {
					System.err.println("put fail");
				}
			}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	public void stop () {
		this.stopFlag = false;
	}
}
