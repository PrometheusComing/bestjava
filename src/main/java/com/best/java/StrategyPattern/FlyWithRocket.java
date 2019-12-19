package com.best.java.StrategyPattern;

public class FlyWithRocket implements FlyAction {
	@Override
	public void fly() {
		System.out.println("I can fly with rocket now");
	}
}
