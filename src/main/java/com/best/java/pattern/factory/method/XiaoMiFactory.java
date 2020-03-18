package com.best.java.pattern.factory.method;

import com.best.java.pattern.factory.easy.Phone;

/**
 * @Author: xjxu3
 * @Date: 2020/3/5 18:27
 * @Description:
 */
public class XiaoMiFactory implements Factory {
	@Override
	public Phone create() {
		return new XiaoMiPhone();
	}
}
