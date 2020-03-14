package com.best.java.concurrent.distributed.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.locks.Lock;

/**
 * @Author: xjxu3
 * @Date: 2020/3/12 16:08
 * @Description: 基于Zookeeper的分布式锁
 * 1.利用节点的名称唯一性加锁，所有客户端使用同一个节点名称创建临时节点，创建成功的拿到锁，失败的监听该节点，等待锁释放通知后竞争锁
 * -->可能有惊群效应，就是当前持有锁的进程释放锁后，大量其他客户端进程收到通知后，都来请求锁，造成大量
 * 竞争.涛哥代码无需监听当前临时节点，拿不到当前文件的锁就获取下一个文件处理，因为一旦一个进程对该文件执行完成，就改变文件后缀，
 * 无需其他进程处理了。
 * 2.利用节点的临时有序节点
 */
public class ZkDistributeLock {
	public static void main(String[] args) {
		CuratorFramework curatorFramework = CuratorFrameworkFactory
				.newClient("192.168.100.1", 5000, 4000, new ExponentialBackoffRetry(1000, 3));
		// 连接zk
		curatorFramework.start();
		InterProcessMutex lock = new InterProcessMutex(curatorFramework, "/locks");
		for (int i = 0; i < 20; i++) {
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + "->尝试获取锁");
				try {
					// 尝试获取锁，拿不到锁会阻塞在这，等待通知
					lock.acquire();
					System.out.println(Thread.currentThread().getName() + "->拿到锁了");
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					// 释放锁
					lock.release();
					System.out.println(Thread.currentThread().getName() + "->释放锁了");
				} catch (Exception e) {
					e.printStackTrace();
				}
			},"T-" + i).start();
		}

	}
}
