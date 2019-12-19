package com.best.java.commandpattern;

/**
 * @Author: xjxu3
 * @Date: 2019/10/10 15:29
 * @Description:
 */
public class FridgeMachine implements Machine {
	@Override
	public void open() {
		System.out.println("fridge is on");
	}

	@Override
	public void close() {
		System.out.println("fridge is down");
	}

	@Override
	public void switchIt() {
		System.out.println("fridge has changed temperature");
	}
}
