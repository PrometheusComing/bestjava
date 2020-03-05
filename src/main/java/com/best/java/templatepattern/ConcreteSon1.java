package com.best.java.templatepattern;

/**
 * @Author: xjxu3
 * @Date: 2020/3/5 20:46
 * @Description:
 *
 * 具体实现类1
 *
 */
public class ConcreteSon1 extends TemplateFather {

	@Override
	protected void step1() {
		System.out.println("ConcreateClassA:step1");
	}
}
