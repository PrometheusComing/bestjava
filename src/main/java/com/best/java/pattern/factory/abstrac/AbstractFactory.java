package com.best.java.pattern.factory.abstrac;

import com.best.java.pattern.factory.easy.Phone;

/**
 * @Author: xjxu3
 * @Date: 2020/3/5 18:31
 * @Description:
 * 抽象工厂模式：每个工厂生产多个产品
 */
public interface AbstractFactory {
	Phone create();

	OS develope();
}
