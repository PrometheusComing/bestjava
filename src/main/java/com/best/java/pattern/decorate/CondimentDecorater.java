package com.best.java.pattern.decorate;

/**
 * 类装饰功能区分出来，依旧要继承父类
 */
public abstract class CondimentDecorater extends Beverage{
	public abstract String getDesc();
}
