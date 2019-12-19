package com.best.java.singletonPattern;

import java.util.HashMap;

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
