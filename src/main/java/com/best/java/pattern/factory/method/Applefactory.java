package com.best.java.pattern.factory.method;

import com.best.java.pattern.factory.easy.Phone;

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
