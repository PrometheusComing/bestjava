package com.best.java.factoryPattern.method;

import com.best.java.factoryPattern.easy.Phone;

/**
 * @Author: xjxu3
 * @Date: 2020/3/5 18:25
 * @Description:
 */
public class Applefactory implements Factory {
	@Override
	public Phone create() {
		return new ApplePhone();
	}
}
