package com.best.java.lambda;

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

	public void print(int value, LambdaInterface<LambdaTest> lambdaInterface) {
		for (int i = 0; i < value; i++) {
			System.out.println("改变值是：" + lambdaInterface.action(this));
			lambdaInterface.act();
		}
	}

	public static void printNum(int value) {
		System.out.println(String.valueOf(19 - value));
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
	}
}
