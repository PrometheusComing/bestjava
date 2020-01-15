package com.best.java.jvm.monitor;

/**
 * @Author: xjxu3
 * @Date: 2020/1/15 9:04
 * @Description: 死锁，Integer缓存了-128-127的对象，所以在这个范围内，valueOf（）取出的都是一个对象。
 * 即Integer.valueOf(1)不管是谁调用，都返回同一个Integer对象。
 */
public class DeadThread implements Runnable {

	private int a;

	private int b;

	public DeadThread(int a, int b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public void run() {
		synchronized (Integer.valueOf(a)) {
			synchronized (Integer.valueOf(b)) {
				System.out.println(a + b);
			}
		}
	}

	public static void main(String[] args) {
		for (int m = 0; m < 100; m++) {
			new Thread(new DeadThread(1, 2)).start();
			new Thread(new DeadThread(2, 1)).start();
		}
	}
}
