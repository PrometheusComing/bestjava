package com.best.java.dynamic;

import java.util.function.Consumer;

/**
 * @Author: xjxu3
 * @Date: 2020/3/30 10:02
 * @Description:
 */
public class DynamicLambda {
	public static void main(String[] args) {
		Consumer<String> c = name -> System.out.println(name);
		c.accept("234");
	}
}
