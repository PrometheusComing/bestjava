package com.best.java.singletonPattern;

class Singleton {
	private Singleton(){
		System.out.println("singleton is create");
	}
	private static Singleton instance = new Singleton();
	public  static Singleton getInstance() {
		return instance;
	}
}
