package com.best.java.service;


import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingDeque;

public class Consumer implements Runnable{
	private BlockingDeque<PCData> blockingDeque ;
	private volatile boolean stopFlag = true;

	public Consumer (BlockingDeque<PCData> blockingDeque) {
		this.blockingDeque = blockingDeque;
	}

	@Override
	public void run() {
		System.out.println("currect produce is :" + Thread.currentThread().getId());
		try {
			while (stopFlag) {
				PCData pcData = blockingDeque.take();
				if (pcData != null) {
					int result = pcData.getData()*(pcData.getData());
					System.out.println(MessageFormat.format("{0} x {1} = {2}",pcData.getData(),
							pcData.getData(),
							result));
				}
				int SLEEP_TIME = 1000;
				Thread.sleep(new Random().nextInt(SLEEP_TIME));
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void stop () {
		this.stopFlag = false;
	}
}
