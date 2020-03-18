package com.best.java.pattern.factory;

import java.util.ArrayList;
import java.util.List;

public abstract class Pizz {
	// 名称
	String name;
	// 面团类型
	String dough;
	// 酱料类型
	String sauce;

	List<String> toppings = new ArrayList<>();

	public void prepare() {
		System.out.println("prepare " + name);
		System.out.println("add dough");
		System.out.println("add sauce");
		System.out.print("add toppings:");
		for (String topping : toppings) {
			System.out.println(" " + topping);
		}
	}

	public void bake() {
		System.out.println("bake 25 minutes");
	}

	public void cut() {
		System.out.println("cut it into diagonal slices");
	}
	public void box() {
		System.out.println("box it now");
	}
}
