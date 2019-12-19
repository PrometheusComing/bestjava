package com.best.java.StrategyPattern;

public class ModeDuck extends Duck {

	ModeDuck(String name) {
		super(name);
		this.setFlyAction(new FlyNoWay());
		this.setVoiceAction(new ZhaZhaVoice());
	}

	@Override
	public void diaplay() {
		System.out.println("I am a " + this.getName());
	}
}
