package com.best.java.pattern.decorate;

/**
 * 摩卡味道，用来装饰饮料Beverage
 */
public class Mocha extends CondimentDecorater {

	private Beverage beverage;

	Mocha(Beverage beverage) {
		this.beverage = beverage;
	}

	@Override
	public String getDesc() {
		return beverage.getDescription() + " mocha ";
	}

	@Override
	public double cost() {
		return beverage.cost() + 5;
	}
}
