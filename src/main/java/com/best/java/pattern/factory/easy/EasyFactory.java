package com.best.java.pattern.factory.easy;

/**
 * @Author: xjxu3
 * @Date: 2020/3/5 18:22
 * @Description:
 *
 * 简单工厂模式：一个工厂类，根据要求生产不同产品
 */
public class EasyFactory {

	Phone createPhone(String brand) {
		if ("hawei".equals(brand)) {
			return new HaWeiPhone();
		} else if ("honor".equals(brand)) {
			return new HonorPhone();
		} else return null;
	}
}
