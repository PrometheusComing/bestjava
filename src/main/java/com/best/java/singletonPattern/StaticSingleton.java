package com.best.java.singletonPattern;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class StaticSingleton {
	private StaticSingleton () {
		System.out.println("StaticSingleton is create");
	}
	private static class SingletonHolder {
		private static StaticSingleton instance = new StaticSingleton();
	}
	public static StaticSingleton getInstance () {
		return SingletonHolder.instance;
	}

	public static void main(String[] args) {
		//使用unsafe破坏单例
		StaticSingleton s1 = StaticSingleton.getInstance();
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			Unsafe unsafe = (Unsafe) field.get(null);
			StaticSingleton s2 = (StaticSingleton)unsafe.allocateInstance(StaticSingleton.class);
			System.out.println(s1 == s2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
