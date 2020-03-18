package com.best.java.pattern.strategy;

public class FlyNoWay implements FlyAction {
	@Override
	public void fly() {
		System.out.println("sory,I can not fly");
	}
}
