package com.best.java.father;

/**
 * @Author: xjxu3
 * @Date: 2019/12/4 11:48
 * @Description:
 */
public class Child extends Father {

	public Child() {
		super(1);
	}

	public Child(int i) {
		super(i);
		System.out.println(111);
	}

	private int age;

	public int getAge() {
		print();
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
