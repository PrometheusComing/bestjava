package com.best.java.concurrent;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
	private static Lock lock = new ReentrantLock();
	private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private static Lock readLock = readWriteLock.readLock();
	private static Lock writeLock = readWriteLock.writeLock();
	private long start = System.currentTimeMillis();
	private int value;
	private long end;

	public int handleRead(Lock lock) throws InterruptedException {
		lock.lock();
		try {
			Thread.sleep(1000);
			end = System.currentTimeMillis();
			System.out.println("耗时:"+ (end - start));
			System.out.println("读操作:"+ value);
			return value;
		} finally {
			lock.unlock();
		}
	}
	public void handleWrite(Lock lock,int index) throws InterruptedException{
		lock.lock();
		try {
			Thread.sleep(1000);
			this.value = index;
			end = System.currentTimeMillis();
			System.out.println("耗时:"+ (end - start));
			System.out.println("写操作:" + index);
		} finally {
			lock.unlock();
		}

	}
    public static void main (String...args)  {
		ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();
		Runnable readable = () -> {
			try {
				readWriteLockTest.handleRead(readLock);
//				app.handleRead(lock);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};

		Runnable writeable = () -> {
			try {
				readWriteLockTest.handleWrite(writeLock,new Random().nextInt(100));
//				app.handleWrite(lock,new Random().nextInt(100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		for(int i = 0;i < 20; i ++){
			new Thread(writeable).start();
		}
		for (int i = 0;i < 18; i ++) {
			new Thread(readable).start();
		}
    }
}

