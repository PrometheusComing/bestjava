package com.best.java.pattern.decorate;

/**
 * 类的核心职责和装饰功能区分开来,其中描述，价格就是装饰功能
 *
 * 定义饮料基类，饮料的描述，饮料的价格（需要具体实现才知道）
 * 饮料的描述，或者说味道，和价格，就是装饰功能，独立成子类
 *
 * ps:可以进一步把原本价格作为私有域，价格计算行为封装出去作为策略。来应对商品满减和打折。即和策略模式一起使用
 */
public abstract class Beverage {
	private String description = "Unknown Beverage";

	public void setDescription(String desc) {
		this.description = desc;
	}

	public String getDescription() {
		return this.description;
	}

	public abstract double cost();
}
