package com.best.java.jvm;

/**
 * @Author: xjxu3
 * @Date: 2020/1/17 8:53
 * @Description: 演示非法向前引用  定义在静态块之后的变量，只可以赋值
 * 不可以访问使用（System.out.println(i);会报非法向前引用）
 */
public class IllegalReference {
	static {
		i = 10;
	}
	static int i;

	public static void main(String[] args) {
		boolean flag = false;
		flag = true;
	}
}
