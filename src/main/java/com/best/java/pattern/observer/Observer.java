package com.best.java.pattern.observer;

public interface Observer {
	// 更新温度、湿度、压力
	void update(float temp,float humi,float pressure);
}
