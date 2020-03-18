package com.best.java.pattern.decorate;

/**
 * 咖啡是一种具体饮料，原味咖啡，没有被装饰过
 */
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
