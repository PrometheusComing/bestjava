package com.best.java.factoryPattern.abstrac;

import com.best.java.factoryPattern.easy.Phone;

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
