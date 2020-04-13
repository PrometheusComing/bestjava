package com.best.java.concurrent;

import sun.misc.Unsafe;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;

/**
 * @Author: xjxu3
 * @Date: 2020/4/10 19:16
 * @Description: 自定义aomicint
 *
 * 有一个用单向链表实现的栈，栈顶为A。
 * 线程T1获取A.next为B，然后希望用CAS将栈顶替换为B，head.compareAndSet(A,B)。
 * 在T1执行上面这条指令之前，线程T2介入，将A、B出栈，再pushD、C、A。此时B.next为null。
 * 此时轮到线程T1执行CAS操作，检测发现栈顶仍为A，所以CAS成功，栈顶变为B。
 * 但实际上B.next为null，其中堆栈中只有B一个元素，C和D组成的链表不再存在于堆栈中，这样就造成C、D被抛弃的现象。
 */
public class MyAomicInt {

	private volatile int value;

	private static long offest;

	private static Unsafe unsafe;

	static {
		try {
			// 反射获取unsafe操作类
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);
			// 获取字段
			Field fieldValue = MyAomicInt.class.getDeclaredField("value");
			// 获取这个字段在类中的偏移量（每个字段在对象里的偏移量一开始就确定了,后续实例中使用时，是在this的基础上计算出该实例
			// 的这个字段的内存地址）
			offest = unsafe.objectFieldOffset(fieldValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MyAomicInt(int initValue) {
		this.value = initValue;
	}

	public int increment(int num) {
		int old;
		int next;
		for (; ; ) {
			// 获取当前值
//			old = unsafe.getIntVolatile(this, offest);
			old = this.value;
			next = old + num;
			if (unsafe.compareAndSwapInt(this, offest, old, next)) {
				return next;
			}
		}
	}


	public int get() {
		return value;
	}

	public static void main(String[] args) {
		MyAomicInt myAomicInt = new MyAomicInt(0);
		Thread[] threads = new Thread[20];
		for (int i = 0; i < 20; i++) {
			threads[i] = new Thread(() -> {
				for (int j = 0; j < 1000; j++) {
					myAomicInt.increment(3);
				}
			});
			threads[i].start();
		}
		join(threads);
		System.out.println("x=" + myAomicInt.get());
	}

	private static void join(Thread... threads) {
		for (int i = 0; i < 20; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
