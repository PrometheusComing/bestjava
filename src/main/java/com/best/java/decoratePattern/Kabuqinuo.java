package com.best.java.decoratePattern;

public class Kabuqinuo extends CondimentDecorater {

	private Beverage beverage;

	Kabuqinuo(Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public String getDesc() {
		return beverage.getDescription() + " Kabuqinuo";
	}

	@Override
	public double cost() {
		return beverage.cost() + 4.90;
	}
}
