package com.best.java.fanshe;

import java.lang.reflect.ReflectPermission;

/**
 * @Author: xjxu3
 * @Date: 2019/12/5 11:21
 * @Description:
 */
public class Car {

	private final int test_version = 1;

	private String brand;
	private String color;
	private int maxSpeed;

	private static SecurityManager manager = new MySecurityManager();
	static {
		try {
			System.out.println("static");
			System.setSecurityManager(manager);
		} catch (SecurityException se) {
			System.out.println("SecurityManager already set!");
		}

	}
	//①默认构造函数
	private Car() {
		Car.manager.checkPermission(new ReflectPermission("suppressAccessChecks"));
	}

	//②带参构造函数
	public Car(String brand, String color, int maxSpeed) {
		this.brand = brand;
		this.color = color;
		this.maxSpeed = maxSpeed;
	}

	private void say() {
		System.out.println("i am a car");
	}

	//③未带参的方法
	public void introduce() {
		System.out.println("brand:" + brand + ";color:" + color + ";maxSpeed:" + maxSpeed);
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
}