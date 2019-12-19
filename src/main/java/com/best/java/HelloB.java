package com.best.java;

/**
 * @Author: xjxu3
 * @Date: 2019/5/7 7:56
 * @Description:
 */
public class HelloB extends HelloA {
	{
		System.out.println("i am b");
	}

	static {
		System.out.println("static b");
	}

	public HelloB() {
		System.out.println("helloB");
	}

	public static void main(String[] args) {
		new HelloB();
		new HelloB();
	}
}

class HelloA {
	{
		System.out.println("i am a");
	}

	static {
		System.out.println("static a");
	}

	public HelloA() {
		System.out.println("helloA");
	}

	public int HelloA() {
		System.out.println(111);
		return 1;
	}
}
