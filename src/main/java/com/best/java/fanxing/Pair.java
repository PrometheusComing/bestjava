package com.best.java.fanxing;

/**
 * @Author: xjxu3
 * @Date: 2019/12/4 10:20
 * @Description:  类型擦除后，编译后的get里有checkcast来恢复
 */

class Pair<T> {
	private T value;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public static void main(String[] args) {
		Pair<String> pair = new Pair<>();
		pair.setValue("123");
		System.out.println(pair.getValue());
	}

}