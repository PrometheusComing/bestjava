package com.best.java.decoratePattern;

public class Coffe extends Beverage {

	Coffe(String str) {
		super();
		this.setDescription(str);
	}

	@Override
	public double cost() {
		return 15;
	}
}
