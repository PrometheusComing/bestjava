package com.best.java.templatepattern;

/**
 * @Author: xjxu3
 * @Date: 2020/3/5 20:43
 * @Description:
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
