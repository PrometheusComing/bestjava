package com.best.java.pattern.chain;

/**
 * @Author: xjxu3
 * @Date: 2020/5/12 15:30
 * @Description: 车辆头部处理类
 */
public class CarHeadHandler extends ChainHandler {
	@Override
	public void handler() {
		System.out.println("处理车头完毕");
		if (this.next != null) {
			next.handler();
		}
	}
}
