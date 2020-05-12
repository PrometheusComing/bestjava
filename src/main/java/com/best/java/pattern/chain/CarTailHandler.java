package com.best.java.pattern.chain;

/**
 * @Author: xjxu3
 * @Date: 2020/5/12 15:31
 * @Description: 车辆尾部处理类
 */
public class CarTailHandler extends ChainHandler {
	@Override
	public void handler() {
		System.out.println("处理车尾完毕");
		if (this.next != null) {
			next.handler();
		}
	}
}
