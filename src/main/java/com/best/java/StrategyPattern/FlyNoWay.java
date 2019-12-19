package com.best.java.StrategyPattern;

public class FlyNoWay implements FlyAction {
	@Override
	public void fly() {
		System.out.println("sory,I can not fly");
	}
}
