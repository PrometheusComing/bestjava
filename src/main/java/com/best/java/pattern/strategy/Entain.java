package com.best.java.pattern.strategy;

public class Entain {
	public static void main (String...args) {
		Duck modelDuck = new ModeDuck("model duck");
		modelDuck.diaplay();
		modelDuck.performFly();
		modelDuck.performVoice();
		modelDuck.setFlyAction(new FlyWithRocket());
		modelDuck.performFly();
	}
}
