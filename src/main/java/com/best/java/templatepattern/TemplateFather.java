package com.best.java.templatepattern;

/**
 * @Author: xjxu3
 * @Date: 2020/3/5 20:43
 * @Description:
 * 模板方法模式（Template Method Pattern） 实际上是封装了一个固定流程，该流程由几个步骤组成，具体步骤可以由子类进行不同实现，从而让固定的流程产生不同的结果。
 *
 * 模板方法模式 非常简单，其实就是类的继承机制，但它却是一个应用非常广泛的模式。
 *
 * 模板方法模式 本质：抽象封装流程，具体进行实现
 *
 * 抽象模板类
 */
public abstract class TemplateFather {

	protected void step1() {
		System.out.println("AbstractClass:step1");
	}

	protected void step2() {
		System.out.println("AbstractClass:step2");
	}

	protected void step3() {
		System.out.println("AbstractClass:step3");
	}

	// 避免子类覆写
	public final void start() {
		this.step1();
		this.step2();
		this.step3();
	}

	public static void main(String[] args) {
		TemplateFather templateFather = new ConcreteSon1();
		templateFather.start();
		templateFather = new ConcreteSon2();
		templateFather.start();
	}

}
