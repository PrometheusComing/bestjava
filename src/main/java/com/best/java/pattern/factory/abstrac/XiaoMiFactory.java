package com.best.java.pattern.factory.abstrac;

import com.best.java.pattern.factory.easy.Phone;
import com.best.java.pattern.factory.method.XiaoMiPhone;

/**
 * @Author: xjxu3
 * @Date: 2020/3/5 18:32
 * @Description:
 */
public class XiaoMiFactory implements AbstractFactory {
	@Override
	public Phone create() {
		return new XiaoMiPhone();
	}

	@Override
	public OS develope() {
		return new MiUiOS();
	}
}
