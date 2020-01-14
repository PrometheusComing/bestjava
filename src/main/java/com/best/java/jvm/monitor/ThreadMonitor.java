package com.best.java.jvm.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: xjxu3
 * @Date: 2020/1/14 15:35
 * @Description:
 */
public class ThreadMonitor {

	// 线程死循环
	public static void createBusyThread(){
		new Thread(() -> {
			while (true);
		},"busyThread").start();

	}

	public static void createLockThread(final Object lock) {
		new Thread(() -> {
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},"lockThread").start();
	}

	public static void main(String[] args) {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			bufferedReader.readLine();
			createBusyThread();
			bufferedReader.readLine();
			byte[] bytes = new byte[] {};
			createLockThread(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
