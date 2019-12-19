package com.best.java;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: xjxu3
 * @Date: 2019/4/26 15:33
 * @Description:
 */
public class Programma2 {

	static class Some {
		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	public static Date getNowTime() {
		return Calendar.getInstance().getTime();
	}

	public static String getSomeValue(Some some) {
		if (some == null) {
			return null;
		}
		if ("123".equals(some.getValue())) {
			return "123";
		} else {
			return some.getValue();
		}
	}

	public static void main(String[] args) {
		Some some = new Some();
		some.setValue("234");
		System.out.println(getSomeValue(some));
		Thread a = new Thread(new Task(true));
		Thread b = new Thread(new Task(false));
		a.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		b.start();
		System.out.println(111);
		getNowTime();
		System.out.println(111222);
		System.out.println(111222333);
		System.out.println(111222444);
		System.out.println(111222555);
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
//		while (true) {
			if (mark) {
				for (int i = 1; i < 20; i += 2) {
					print(i);
				}
//				break;
			} else {
				for (int i = 2; i < 20; i += 2) {
					print(i);
				}
//				break;
			}
//		}

	}
}


