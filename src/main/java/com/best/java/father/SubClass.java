package com.best.java.father;

/**
 * @Author: xjxu3
 * @Date: 2019/5/7 7:56
 * @Description: 子类继承父类后，获取到父类的属性和方法，这些属性和方法在使用前必须先初始化，所以须先调用父类的构造器进行初始化
 * super并不表示对象，只是表示字段或者方法来源于父类
 */
public class SubClass extends SuperClass {
	{
		System.out.println("i am SubClass");
	}

	static {
		System.out.println("SubClass static");
	}

	 SubClass() {
		System.out.println("SubClass construct");
	}

	public static void main(String[] args) {
		new SubClass();
		new SubClass().SuperClass();
	}
}

class SuperClass {
	{
		System.out.println("i am SuperClass");
	}

	static {
		System.out.println("SuperClass static");
	}

	 SuperClass() {
		System.out.println("SuperClass construct");
	}

	public int SuperClass() {
		System.out.println(111);
		return 1;
	}
}
