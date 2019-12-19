package com.best.java.singletonPattern;

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
