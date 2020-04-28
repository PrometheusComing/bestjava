package com.best.java.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: xjxu3
 * @Date: 2020/4/27 16:34
 * @Description:
 */
public class MyDemotionLock {

	private Map<String, Object> map = new HashMap<>();

	private ReadWriteLock rwl = new ReentrantReadWriteLock();

	private Lock r = rwl.readLock();
	private Lock w = rwl.writeLock();

	Lock lock = new ReentrantLock();
	int value = 100;


	public void readAndWrite() {
		lock.newCondition();
		//获取读锁
		r.lock();
		Object object = map.get("a");
		if (object == null) {
			System.out.println(Thread.currentThread().getName() + "获取到了空值");
			//缓存是空，模拟从新从数据库中获取
			//关闭读锁，获取写锁
			r.unlock();
			w.lock();
			map.put("a", Thread.currentThread().getName() + value);
			r.lock();
			//完成写操作后，应该在写锁释放之前获取到读锁
			w.unlock();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			r.lock();
		}
		System.out.println("线程" + Thread.currentThread().getName() + "————" + map.get("a"));
		r.unlock();
	}

	public static void main(String[] args) {

		MyDemotionLock lock = new MyDemotionLock();
		Runnable runnable = () -> {
			System.out.println(Thread.currentThread().getName() + "启动");
			lock.readAndWrite();
		};
		Thread[] threadArray = new Thread[10];
		for (int i = 0; i < 10; i++) {
			threadArray[i] = new Thread(runnable);
		}
		for (int i = 0; i < 10; i++) {
			threadArray[i].start();
		}
	}

}

