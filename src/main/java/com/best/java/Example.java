package com.best.java;

/**
 * @Author: xjxu3
 * @Date: 2019/5/7 8:06
 * @Description:
 */
public class Example {
	String str = new String("good");
	int m = new Integer(1);
	char[] ch = {'a','b','c'};

	public static void main(String[] args) {
		Example example = new Example();
		example.change(example.m,example.ch);
		System.out.println(example.m);
		System.out.println(example.ch);
	}
	public void change(String str,char[] ch) {
		str = "test ok";
		ch[0] = 'g';
	}

	public void change(int str,char[] ch) {
		str = 100;
		ch[0] = 'g';
	}
}
