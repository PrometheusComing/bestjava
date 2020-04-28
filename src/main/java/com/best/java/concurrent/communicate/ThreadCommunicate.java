package com.best.java.concurrent.communicate;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: xjxu3
 * @Date: 2020/3/18 22:07
 * @Description: 线程通讯测试
 * 线程A,B,C交替打印A,B,C,如A B C A B C
 * 耐心点，从两个线程通信的情况进行分析，慢慢分析
 */
public class ThreadCommunicate {

	static class PrintA implements Runnable {

		private Lock lockAB;
		private Lock lockCA;

		private Condition conditionB;
		private Condition conditionA;

		public PrintA(Lock lockAB, Lock lockCA, Condition conditionB, Condition conditionA) {
			this.lockAB = lockAB;
			this.lockCA = lockCA;
			this.conditionB = conditionB;
			this.conditionA = conditionA;
		}

		@Override
		public void run() {
			for (; ; ) {
				lockCA.lock();
				lockAB.lock();
				conditionB.signal();
				System.out.println(" A ");
				lockAB.unlock();
				try {
					conditionA.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				} finally {
					lockCA.unlock();
				}
			}

		}
	}

	static class PrintB implements Runnable {

		private Lock lockAB;
		private Lock lockBC;
		private Condition conditionB;
		private Condition conditionC;

		public PrintB(Lock lockAB, Lock lockBC, Condition conditionB, Condition conditionC) {
			this.lockAB = lockAB;
			this.lockBC = lockBC;
			this.conditionB = conditionB;
			this.conditionC = conditionC;
		}

		@Override
		public void run() {
			for (; ; ) {
				lockAB.lock();
				lockBC.lock();
				conditionC.signal();
				System.out.println(" B ");
				lockBC.unlock();
				try {
					conditionB.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				} finally {
					lockAB.unlock();
				}
			}

		}
	}

	static class PrintC implements Runnable {
		private Lock lockBC;
		private Lock lockCA;

		private Condition conditionA;
		private Condition conditionC;

		public PrintC(Lock lockBC, Lock lockCA, Condition conditionA, Condition conditionC) {
			this.lockBC = lockBC;
			this.lockCA = lockCA;
			this.conditionA = conditionA;
			this.conditionC = conditionC;
		}

		@Override
		public void run() {
			for (; ; ) {
				lockBC.lock();
				lockCA.lock();
				conditionA.signal();
				System.out.println(" C ");
				lockCA.unlock();
				try {
					conditionC.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				} finally {
					lockBC.unlock();
				}
			}

		}
	}

	public static void main(String[] args) {
		Lock lockAB = new ReentrantLock();
		Lock lockBC = new ReentrantLock();
		Lock lockCA = new ReentrantLock();
		Condition conditionA = lockCA.newCondition();
		Condition conditionB = lockAB.newCondition();
		Condition conditionC = lockBC.newCondition();
		new Thread(new PrintA(lockAB,lockCA,conditionB,conditionA),"Thread-A").start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(new PrintB(lockAB,lockBC,conditionB,conditionC),"Thread-B").start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(new PrintC(lockBC,lockCA,conditionA,conditionC),"Thread-C").start();
	}
}
