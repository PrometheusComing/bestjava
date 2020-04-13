package com.best.java.pattern.singleton;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: xjxu3
 * @Date: 2020/4/10 17:18
 * @Description: cas实现单例
 */
public class CasSingleton {

	private static final AtomicReference<CasSingleton> INSTANCE = new AtomicReference<CasSingleton>();

	private CasSingleton() {}

	public static CasSingleton getInstance() {
		for (;;) {
			CasSingleton singleton = INSTANCE.get();
			if (null != singleton) {
				return singleton;
			}

			singleton = new CasSingleton();
			if (INSTANCE.compareAndSet(null, singleton)) {
				return singleton;
			}
		}
	}
}
