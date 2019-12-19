package com.best.java.singletonPattern;

public class StaticSingleton {
	private StaticSingleton () {
		System.out.println("StaticSingleton is create");
	}
	private static class SingletonHolder {
		private static StaticSingleton instance = new StaticSingleton();
	}
	public StaticSingleton getInstance () {
		return SingletonHolder.instance;
	}
}
