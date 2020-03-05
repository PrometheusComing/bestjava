package com.best.java.singletonPattern;

// 饿汉模式，饥饿的直接new了
class Singleton {
	private Singleton(){
		System.out.println("singleton is create");
	}
	private static Singleton instance = new Singleton();
	public  static Singleton getInstance() {
		return instance;
	}
}
