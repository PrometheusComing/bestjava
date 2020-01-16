package com.best.java.jvm.init;

/**
 * @Author: xjxu3
 * @Date: 2019/12/16 16:31
 * @Description:
 */
public class ConstClass {
	static {
		System.out.println("ConstClass init!");
	}

	public static final String HELLO_WORLD = new String("hello world");
//	public static final String HELLO_WORLD = "hello world";
}
