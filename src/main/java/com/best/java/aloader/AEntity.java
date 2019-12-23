package com.best.java.aloader;

/**
 * @Author: xjxu3
 * @Date: 2019/12/20 17:14
 * @Description:
 */
public class AEntity {

	static {
		System.out.println("AEntity class load : " + AEntity.class.getClassLoader());
	}

	private String name;

	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
