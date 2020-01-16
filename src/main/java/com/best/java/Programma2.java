package com.best.java;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: xjxu3
 * @Date: 2019/4/26 15:33
 * @Description: 双线程交替打印数字
 */
public class Programma2 {
	public static void main(String[] args) {
		Thread a = new Thread(new Task(true));
		Thread b = new Thread(new Task(false));
		a.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		b.start();
	}
}

class Task implements Runnable {

	private boolean mark;

	private static Lock lock = new ReentrantLock();

	private static Condition condition = lock.newCondition();

	public Task(boolean mark) {
		this.mark = mark;
	}

	public void print(int i) {
		lock.lock();
		condition.signal();
		System.out.println(Thread.currentThread().getName() + "--" + i);
		try {
			condition.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void run() {
		if (mark) {
			for (int i = 1; i < 20; i += 2) {
				print(i);
			}
		} else {
			for (int i = 2; i < 20; i += 2) {
				print(i);
			}
		}
	}
}


