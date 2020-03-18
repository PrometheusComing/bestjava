package com.best.java.pattern.singleton;

// 饱汉模式，使用时才实例化，懒加载
// 因为 singleton = new Singleton() 这句话可以分为三步：
//     1. 为 singleton 分配内存空间；
//     2. 初始化 singleton；
//     3. 将 singleton 指向分配的内存空间。
//     但是由于JVM具有指令重排的特性，执行顺序有可能变成 1-3-2。 指令重排在单线程下不会出现问题，但是在多线程下会导致一个线程获得一个未初始化的实例。例如：线程T1执行了1和3，此时T2调用 getInstance() 后发现 singleton 不为空，因此返回 singleton， 但是此时的 singleton 还没有被初始化。
//     使用 volatile 会禁止JVM指令重排，从而保证在多线程下也能正常执行。
public class DoubleLockSingleton {
	private DoubleLockSingleton() {
		System.out.println("DoubleLockSingleton is created");
	}
	private volatile DoubleLockSingleton doubleLockSingleton;

	public DoubleLockSingleton getDoubleLockSingleton() {
		if(doubleLockSingleton == null) {
			synchronized (DoubleLockSingleton.class) {
				if (doubleLockSingleton == null) {
					doubleLockSingleton = new DoubleLockSingleton();
				}
			}
		}
		return doubleLockSingleton;
	}
}
