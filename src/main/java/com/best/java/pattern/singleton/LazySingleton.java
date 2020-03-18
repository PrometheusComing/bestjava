package com.best.java.pattern.singleton;
// 懒汉模式，会有多线程问题，加锁解决
public class LazySingleton {
	private LazySingleton() {
		System.out.println("LazySingleton is create ");
	}
	private static LazySingleton lazySingleton = null;
	public static LazySingleton getLazySingleton () {
		if (lazySingleton == null) {
			return new LazySingleton();
		} else {
			return lazySingleton;
		}
	}
}
