package com.best.java.pattern.chain;

/**
 * @Author: xjxu3
 * @Date: 2020/5/12 15:31
 * @Description: 车身处理
 */
public class CarBodyHandler extends ChainHandler {
	@Override
	public void handler() {
		System.out.println("处理车身完毕");
		if (this.next != null) {
			next.handler();
		}
	}
}
