package com.best.java.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: xjxu3
 * @Date: 2020/4/27 10:17
 * @Description: 可重入互斥锁
 */
public class MutexLock implements Lock {

	private final Sync sync = new Sync();

	private static class Sync extends AbstractQueuedSynchronizer {

		private static final long serialVersionUID = -3678781406338227067L;

		@Override
		protected boolean isHeldExclusively() {
			return getState() == 1 ;
		}

		@Override
		protected boolean tryAcquire(int arg) {
			final Thread thread = Thread.currentThread();
			int c = getState();
			if (c == 0) {
				if (compareAndSetState(0,1)) {
					setExclusiveOwnerThread(thread);
					return true;
				}
			} else if (thread == getExclusiveOwnerThread()) {
				int nextc = c + arg;
				if (nextc < 0)
					throw new Error("Maximum lock count exceeded");
				setState(nextc);
				return true;
			}
			return false;
		}

		@Override
		protected boolean tryRelease(int arg) {
			if (Thread.currentThread() != getExclusiveOwnerThread()) {
				throw new IllegalMonitorStateException();
			}
			int c = getState() - arg;
			boolean flag = false;
			if (c == 0) {
				setExclusiveOwnerThread(null);
				flag = true;
			}
			setState(c);
			return flag;

		}
		Condition newCondition() {
			return new ConditionObject();
		}
	}

	@Override
	public void lock() {
		sync.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() {
		sync.release(1);
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}
}
