package com.best.java.pattern.decorate;

/**
 * 类装饰功能区分出来，依旧要继承父类
 * 把需要装饰的部分拿出来
 * cost由于父类也是抽象，可以省略
 */
public abstract class CondimentDecorater extends Beverage{

	public abstract String getDesc();

	public abstract double cost();

}
