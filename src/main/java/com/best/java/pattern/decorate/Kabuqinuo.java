package com.best.java.pattern.decorate;
/**
 * 卡布奇诺味道，用来装饰饮料Beverage
 * 没有饮料，直接new味道是无用的
 */
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
