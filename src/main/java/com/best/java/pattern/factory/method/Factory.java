package com.best.java.pattern.factory.method;

import com.best.java.pattern.factory.easy.Phone;

/**
 * @Author: xjxu3
 * @Date: 2020/3/5 18:25
 * @Description:
 * 工厂方法模式：一个工厂接口，多个工厂类，不同工厂生产不同产品
 * 工厂方法模式的改进在于，减轻了简单工厂模式中工厂类的复杂度，具体的产出交由具体的工厂类，降低了耦合度
 */
public interface Factory {
	Phone create();
}
