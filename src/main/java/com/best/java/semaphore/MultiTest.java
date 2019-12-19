package com.best.java.semaphore;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * @Author: xjxu3
 * @Date: 2019/4/10 17:29
 * @Description:
 */
public class MultiTest {

	private int a = 10;




	public MultiTest(int b) {
		this.a = b;
	}

	public MultiTest() {

	}

	public static void main(String[] args) {
		MultiTest multiTestJi = new MultiTest();
		System.out.println(multiTestJi.a);
		Class<?> test = multiTestJi.getClass();
		ClassLoader classLoader = test.getClassLoader();
		try {
			Class<?> loadClass = classLoader.loadClass("com.best.java.semaphore.MultiTest");
			Constructor<?> constructor = loadClass.getDeclaredConstructor();
			MultiTest tt = (MultiTest) constructor.newInstance();
			System.out.println(tt.a);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}


		try {
			Constructor<?> constructors = test.getConstructor(int.class);
			multiTestJi = (MultiTest) constructors.newInstance(20);
			System.out.println(multiTestJi.a);
		} catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
			e.printStackTrace();
		}
//		Thread t1 = new Thread(new LunXun(multiTestJi, 1, true));
//		Thread t2 = new Thread(new LunXun(multiTestJi, 2, false));
//		t1.start();
//		t2.start();
	}
}

class LunXun implements Runnable {

	private final MultiTest multiTestJi;

	private boolean isJi;

	private int i;

	public LunXun(MultiTest multiTestJi, int i, boolean isji) {
		this.multiTestJi = multiTestJi;
		this.i = i;
		this.isJi = isji;
	}

	@Override
	public void run() {
		while (i < 100) {
			synchronized (multiTestJi) {
				if (isJi) {
					try {
						multiTestJi.notify();
						System.out.println(Thread.currentThread().getName() + "打印了奇数" + i);
						i = i + 2;
						multiTestJi.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					try {
						multiTestJi.notify();
						System.out.println(Thread.currentThread().getName() + "打印了偶数" + i);
						i = i + 2;
						multiTestJi.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}
}
