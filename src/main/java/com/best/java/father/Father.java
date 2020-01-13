package com.best.java.father;

import com.best.java.fanshe.FansheTest;

/**
 * @Author: xjxu3
 * @Date: 2019/12/4 11:48
 * @Description:
 */
public class Father {

	public static void print() {
		System.out.println("it is father static");
	}

	public Father(int i) {
		System.out.println(222);
	}
	private String name;

	private String rela;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRela() {
		return rela;
	}

	public void setRela(String rela) {
		this.rela = rela;
	}
}
