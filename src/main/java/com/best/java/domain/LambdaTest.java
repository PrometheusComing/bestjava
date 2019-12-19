package com.best.java.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class LambdaTest {

	private String value;

	private Date modifyTime;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public void print(int value, LambdaInterface lambdaInterface) {
		for (int i = 0; i < value; i++) {
			System.out.println("改变值是：" + lambdaInterface.action(this));
			lambdaInterface.act();
		}
	}

	public static void printNum(int value) {
		System.out.println(String.valueOf(19 - value));
	}

	LambdaTest(int p) {
		System.out.println(111);
	}

	LambdaTest(String p) {
		System.out.println(121);
	}

	LambdaTest() {
		System.out.println(131);
	}

	public static void main(String[] args) {
		LambdaTest lambdaTest = new LambdaTest();
		lambdaTest.setValue("value is exist,no change");
//        lambdaTest.print(3, LambdaTest::printNum);
		lambdaTest.print(1, v -> {
			v.setModifyTime(Calendar.getInstance().getTime());
			if ("".equals(v.getValue()) || v.getValue() == null) {
				v.setValue("first change");
				return 1;
			} else return 0;
		});
		System.out.println(lambdaTest.value);
		System.out.println(lambdaTest.modifyTime);
		List<String> list = new ArrayList<String>() {{
			add("11111");
			add("123");
		}};
		list.sort(Comparator.comparingInt(String::length));
		list.forEach(System.out::println);

	}
}
