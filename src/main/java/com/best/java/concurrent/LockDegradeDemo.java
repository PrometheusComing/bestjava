package com.best.java.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: xjxu3
 * @Date: 2020/4/27 15:58
 * @Description: 锁降级是指，当前拥有写锁，再获取到
 * 读锁，随后释放（先前拥有的）写锁的过程，相当于能从写锁直接拿到读锁，无需再竞争
 *
 * 具体要看业务场景要求了，未必一定要使用锁降级
 *
 * 注释27行，解开38行注释，观察结果的不一致
 *
 * demo2 见MyDemotionLock
 */
public class LockDegradeDemo {
	private int i = 0;

	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private Lock writerLock = readWriteLock.writeLock();
	private Lock readLock = readWriteLock.writeLock();

	public void doSomething() {
		writerLock.lock();
		try {
			i++;
			// 在写锁里直接获得读锁
			readLock.lock();
		} finally {
			writerLock.unlock();
		}
		try {
			// 故意增加sleep，让38行的读锁的获取比其他线程在23行写锁的获取晚一些
			Thread.sleep(1L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 如果在写锁外再次获取读锁，由于其他线程通过写锁再次修改了i的值，导致这里的读的i已经变了
//		readLock.lock();
		try {
			System.out.println("i = " + i);
		} finally {
			readLock.unlock();
		}
	}

	public static void main(String[] args) {
		LockDegradeDemo lockDegradeDemo = new LockDegradeDemo();
		for (int i = 0; i < 50; i++) {
			new Thread(lockDegradeDemo::doSomething).start();
		}
	}
}

