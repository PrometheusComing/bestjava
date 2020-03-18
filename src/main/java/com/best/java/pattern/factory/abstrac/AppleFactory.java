package com.best.java.pattern.factory.abstrac;

import com.best.java.pattern.factory.easy.Phone;
import com.best.java.pattern.factory.method.ApplePhone;

/**
 * @Author: xjxu3
 * @Date: 2020/3/5 18:33
 * @Description:
 */
public class AppleFactory implements AbstractFactory {
	@Override
	public Phone create() {
		return new ApplePhone();
	}

	@Override
	public OS develope() {
		return new IOS();
	}
}
