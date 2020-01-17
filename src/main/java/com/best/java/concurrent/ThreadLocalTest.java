package com.best.java.concurrent;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: xjxu3
 * @Date: 2020/1/17 14:30
 * @Description: ThreadLocal测试
 *
 * Thread里有个ThreadLocalMap类型的成员变量threadLocals,里面有个Entry[]，entry的key就是ThreadLocal的hash，value就是存的类型
 * （本例就是SimpleDateFormat）。所以其实副本是存在当前线程自己的ThreadLocalMap里的，要用ThreadLocal的hash去取，拿不到就set
 * 初始化的值，取到就直接使用
 *
 * ThreadLocalMap是ThreadLocal的静态内部类
 * Entry是ThreadLocalMap的静态内部类
 * ThreadLocalMap里有个Entry[]数组 table
 */
public class ThreadLocalTest implements Runnable {
	private static SimpleDateFormat simpleDateFormat1;
	private static SimpleDateFormat simpleDateFormat2;

	private static volatile boolean flag = true;

	private static ThreadLocal<SimpleDateFormat> FORMAT_LOCAL = ThreadLocal
			.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void print2() {
		Thread t = Thread.currentThread();
		try {
			System.out.println(t.getName() +
					" 时间是：" + FORMAT_LOCAL.get().parse("2020-01-22 15:45:36") + FORMAT_LOCAL.get().toString());
			if (flag) {
				System.out.println("flag true");
				simpleDateFormat1 = FORMAT_LOCAL.get();
				flag = false;
			} else {
				System.out.println("flag false");
				simpleDateFormat2 = FORMAT_LOCAL.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void print() {
		Thread t = Thread.currentThread();
		try {
			System.out.println(t.getName() +
					" 时间是：" + simpleDateFormat.parse("2020-01-22 15:45:36") + simpleDateFormat.hashCode());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		print2();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			new Thread(new ThreadLocalTest(), "thread-" + i).start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(simpleDateFormat1 == simpleDateFormat2);
	}
}
