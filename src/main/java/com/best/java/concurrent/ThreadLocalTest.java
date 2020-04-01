package com.best.java.concurrent;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Author: xjxu3
 * @Date: 2020/1/17 14:30
 * @Description: ThreadLocal测试类
 *原理：
 * Thread里有个ThreadLocalMap类型的成员变量threadLocals,里面有个Entry[]，entry的key就是ThreadLocal的实例FORMAT_LOCAL，value就是存的类型
 * （本例就是SimpleDateFormat）。所以其实副本是存在当前线程自己的ThreadLocalMap里的，要用ThreadLocal实例的hash去取在Entry[]中的位置，拿不到就调用
 * 初始化方法initialValue(),然后再set进去
 * 取到就拿出对应的value实例SimpleDateFormat进行使用
 *
 * ThreadLocalMap是ThreadLocal的静态内部类
 * Entry是ThreadLocalMap的静态内部类
 * ThreadLocalMap里有个Entry[]数组 table
 * Entry中的ThreadLocal是弱引用，垃圾回收扫到就会回收这个对象。
 *
 * 强引用：只要某个对象有强引用与之关联，JVM必定不会回收这个对象，即使在内存不足的情况下，JVM宁愿抛出OutOfMemory错误也不会回收这种对象
 * 软引用：软引用是用来描述一些有用但并不是必需的对象，在Java中用java.lang.ref.SoftReference类来表示。只有在内存不足的时候JVM才会回收该对象
 * 可以用来实现缓存
 * 弱引用：只具有弱引用的对象拥有更短暂的生命周期。在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，
 * 不管当前内存空间足够与否，都会回收它的内存。不过，由于垃圾回收器是一个优先级很低的线程，因此不一定会很快发现那些只具有弱引用的对象。
 * 虚引用：也称为幻影引用，一个对象是都有虚引用的存在都不会对生存时间都构成影响，也无法通过虚引用来获取对一个对象的真实引用。唯一的用处：能在对象被GC时收到系统通知，JAVA中用PhantomReference来实现虚引用
 *
 * 造成内存泄漏，最后出现内存溢出的原因（根据本例）：
 * 当把FORMAT_LOCAL变量置为null以后，就没有任何强引用指向FORMAT_LOCAL实例，所以FORMAT_LOCAL实例将会被gc回收。
 * 这样一来，ThreadLocalMap中就会出现key为null的Entry，就没有办法访问这些key为null的Entry的value，
 * 如果当前线程再迟迟不结束的话，这些key为null的Entry的value就会一直存在一条强引用链：
 * Thread Ref -> Thread -> ThreaLocalMap -> Entry -> value，而这块value永远不会被访问到了，所以存在着内存泄露。
 *
 * 解决：最好的做法是不在需要使用ThreadLocal变量后，都调用它的remove()方法，清除数据。 调用remove()方法最佳时机是线程运行结束
 * 之前的finally代码块中调用，这样能完全避免操作不当导致的内存泄漏，这种主动清理的方式比惰性删除有效。
 *
 * 不使用软引用是因为等到内存吃紧回收的时候，强引用还在，无法回收，一样oom
 *
 * InheritableThreadLocal 父线程使用后，子线程可以直接继承使用和父线程同一个对象
 *
 * ps:
 * key使用强引用：在当前ThreadLocal没有外部强引用时，ThreadLocalMap的Entry还保持着ThreadLocal的强引用，
 * ThreadLocal不会被GC。如果没有手动删除，并且当前线程在线程池中不结束，就导致了Entry的内存泄漏。（有点类似用static修饰ThreadLocal的情况）
 *
 * key使用弱引用：在当前ThreadLocal没有外部强引用时，ThreadLocalMap只保持着ThreadLocal的弱引用，
 * 无论有没有手动删除，ThreadLocal都会被GC,如果后续该线程能继续执行get,set,remove方法，就有机会执行cleanSomeSlots()，expungeStaleEntry()
 * 从而删除那些key为null，value还在的entry。比如ThreadLocalA 和ThreadLocalB，A失去了强引用，但是当前线程里还有value的引用，
 * 这时候当前线程首次执行ThreadLocalB.get()，会执行map.getEntry(this);显然ThreadLocalB不在map中，进而执行getEntryAfterMiss删除
 * A里那个key变成null的entry。有点类似与惰性删除

 *
 * 所以弱引用只是多了一层保障
 *
 * 所以一定要手动删除
 *
 */
public class ThreadLocalTest implements Runnable {

	private static SimpleDateFormat simpleDateFormat1;
	private static SimpleDateFormat simpleDateFormat2;

	private static volatile boolean flag = true;

	private static ThreadLocal<SimpleDateFormat> FORMAT_LOCAL = ThreadLocal
			.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


	private static InheritableThreadLocal<SimpleDateFormat> INHERI_FORMAT_LOCAL = new InheritableThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};


	public void print2() {
		Thread t = Thread.currentThread();
		try {
			System.out.println(t.getName() +
					" 时间是：" + INHERI_FORMAT_LOCAL.get().parse("2020-01-22 15:45:36") + INHERI_FORMAT_LOCAL.get().toString());
			if (flag) {
				System.out.println("flag true");
				simpleDateFormat1 = INHERI_FORMAT_LOCAL.get();
				flag = false;
			} else {
				System.out.println("flag false");
				simpleDateFormat2 = INHERI_FORMAT_LOCAL.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		finally {
//			FORMAT_LOCAL.remove();
//		}
	}

	public void print1() {
		Thread t = Thread.currentThread();
		try {
			System.out.println(t.getName() +
					" 时间是：" + FORMAT_LOCAL.get().parse("2020-01-22 15:45:36") + FORMAT_LOCAL.get().toString());
			if (flag) {
				System.out.println("flag true");
				simpleDateFormat1 = FORMAT_LOCAL.get();
				flag = false;
			} else {
				System.out.println("flag false");
				simpleDateFormat2 = FORMAT_LOCAL.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		finally {
			FORMAT_LOCAL.remove();
//		}
	}

	@Override
	public void run() {
		print1();
	}

	public static void main(String[] args) {
		Thread thread = Thread.currentThread();

		// INHERI_FORMAT_LOCAL,父子线程中，获取的对象是同一个
		try {
			System.out.println(thread.getName() +
					" 时间是：" + INHERI_FORMAT_LOCAL.get().parse("2020-01-22 15:45:36")
					+ INHERI_FORMAT_LOCAL.get().toString()
			);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		//  FORMAT_LOCAL，父子线程中，获取的对象是共有3个
//		try {
//			System.out.println(thread.getName() +
//					" 时间是：" + FORMAT_LOCAL.get().parse("2020-01-22 15:45:36")
//					+ FORMAT_LOCAL.get().toString()
//			);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}


		for (int i = 0; i < 2; i++) {
			new Thread(new ThreadLocalTest(), "thread-" + i).start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(simpleDateFormat1 == simpleDateFormat2);
	}
}
