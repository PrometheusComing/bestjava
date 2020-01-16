package com.best.java.jvm.init;

/**
 * @Author: xjxu3
 * @Date: 2020/1/16 10:52
 * @Description: 类的被动引用
 * 1.通过子类引用父类的静态字段，不会引发子类的初始化,加载和验证阶段不一定，看具体虚拟机
 * 2.通过数组的定义引用类，不会触发初始化
 */
public class PassiveReference {
	public static void main(String[] args) {
//		System.out.println(Son.value);
		Father[] fathers = new Father[10];
	}
}

class Father {
	static {
		System.out.println("father init");
	}

	public static int value = 10;
}

class Son extends Father {
	static {
		System.out.println("son init");
	}
}
