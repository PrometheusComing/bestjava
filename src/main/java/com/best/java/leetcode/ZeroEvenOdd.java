package com.best.java.leetcode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * @Author: xjxu3
 * @Date: 2020/4/6 20:51
 * @Description:  交替打印01020304
 */
public class ZeroEvenOdd {
	private int n;

	public ZeroEvenOdd(int n) {
		this.n = n;
	}


	Lock lock = new ReentrantLock();

	private volatile int flag = 0;


	private Condition conditionOdd = lock.newCondition();
	private Condition conditionEven = lock.newCondition();

	// printNumber.accept(x) outputs "x", where x is an integer.
	public void zero(IntConsumer printNumber) throws InterruptedException {
		int i = 1;
		while (i <= n) {
			lock.lock();
			if (flag == 0) {
				printNumber.accept(0);
				if (i % 2 == 1) {
					conditionOdd.signal();
					flag = 1;
					conditionOdd.await();
				} else {
					conditionEven.signal();
					flag = 2;
					conditionEven.await();
				}
				i = i + 1;
			} else {
				lock.unlock();
			}
		}
		lock.unlock();
	}

	public void even(IntConsumer printNumber) throws InterruptedException {
		int i = 2;
		while (i <= 2 * n) {
			lock.lock();
			if (flag == 2) {
				conditionEven.signal();
				printNumber.accept(i);
				flag = 0;
				conditionEven.await();
				i = i + 2;
			} else {
				lock.unlock();
			}
		}
		lock.unlock();
	}

	public void odd(IntConsumer printNumber) throws InterruptedException {
		int i = 1;
		while (i < 2 * n) {
			lock.lock();
			if (flag == 1) {
				conditionOdd.signal();
				printNumber.accept(i);
				flag = 0;
				conditionOdd.await();
				i = i + 2;
			} else {
				lock.unlock();
			}
		}
		lock.unlock();
	}

	public static void main(String[] args) throws Exception {
		ZeroEvenOdd evenOdd = new ZeroEvenOdd(8);
		IntConsumer printNumber = new IntConsumer() {
			@Override
			public void accept(int value) {
				System.out.println(value);
			}
		};
		new Thread(() -> {
			try {
				evenOdd.zero(printNumber);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
//		Thread.sleep(1000);
		new Thread(() -> {
			try {
				evenOdd.even(printNumber);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		new Thread(() -> {
			try {
				evenOdd.odd(printNumber);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
