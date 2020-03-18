package com.best.java.pattern.decorate;

/**
 * 类的核心职责和装饰功能区分开来,其中描述，价格就是装饰功能
 *
 * 定义饮料基类，饮料的描述，饮料的价格（需要具体实现才知道）
 * 饮料的描述，或者说味道，和价格，就是装饰功能，独立成子类
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
