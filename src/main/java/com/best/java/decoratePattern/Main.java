package com.best.java.decoratePattern;

public class Main {
	public static void main (String...args) {
		Beverage beverage = new Coffe("I am coffe");
		System.out.println("咖啡原味价格是 ：" + beverage.cost());
		System.out.println(beverage.getDescription());
		Mocha mocha = new Mocha(beverage);
		System.out.println("摩卡咖啡价格是： " + mocha.cost());
		System.out.println(mocha.getDescription());
		Kabuqinuo kabuqinuo = new Kabuqinuo(beverage);
		System.out.println("卡布奇诺价格是： " + kabuqinuo.cost());
		System.out.println(kabuqinuo.getDescription());
		Kabuqinuo kabuqinuo1 = new Kabuqinuo(mocha);
		System.out.println("摩卡加卡布奇诺价格是：" + kabuqinuo1.cost());
		System.out.println(kabuqinuo1.getDescription());
	}
}
