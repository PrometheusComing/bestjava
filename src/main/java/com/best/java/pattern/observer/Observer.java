package com.best.java.pattern.observer;

public interface Observer {
	// 引用subject，观察者自己更新自己需要的字段
	void update(Subject subject);
}
