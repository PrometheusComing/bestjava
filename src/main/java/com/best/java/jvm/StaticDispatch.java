package com.best.java.jvm;

/**
 * @Author: xjxu3
 * @Date: 2020/1/13 16:30
 * @Description: 静态分派
 * 静态分派发生在编译阶段,因此确定静态分派的动作实际上不是由虚拟机来执行的。
 * 另外,编译器虽然能确定出方法的重载版本,但在很多情况下这个重载版本并不是“唯一的”,往往只能确定一个“更加合适的”版本
 */
public class StaticDispatch {

	static abstract class Human {
	}

	static class Man extends Human {
	}

	static class Woman extends Human {
	}

	public void sayHello(Human guy) {
		System.out.println("hello,guy!");
	}

	public void sayHello(Man guy) {
		System.out.println("hello,gentleman!");
	}

	public void sayHello(Woman guy) {
		System.out.println("hello,lady!");
	}

	public static void main(String[] args) {
		Human man = new Man();
		Human woman = new Woman();
		StaticDispatch sr = new StaticDispatch();
		sr.sayHello(man);
		sr.sayHello(woman);
	}

}

