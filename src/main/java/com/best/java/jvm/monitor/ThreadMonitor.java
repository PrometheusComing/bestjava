package com.best.java.jvm.monitor;

import java.io.BufferedReader;
import java.io.InputStreamReader;


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

	public static void createLockThread(final Object lock,final String name) {
		new Thread(() -> {
			synchronized (lock) {
				try {
					Thread.sleep(10000);
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},name).start();
	}

	public static void main(String[] args) {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			bufferedReader.readLine();
			createBusyThread();
			bufferedReader.readLine();
			byte[] bytes = new byte[] {};
			createLockThread(bytes,"waitThread");
			Thread.sleep(1000);
			createLockThread(bytes,"blockThread");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
