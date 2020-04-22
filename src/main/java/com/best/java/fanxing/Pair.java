package com.best.java.fanxing;

/**
 * @Author: xjxu3
 * @Date: 2019/12/4 10:20
 * @Description:  类型擦除
 * LocalVariableTypeTable里会记录方法栈帧中记录的泛型类型（setValue和main方法里一个是Pair<TT;>，一个是String）
 * checkcast     #9                  // class java/lang/String
 * checkcast会拿当前操作数栈顶的对象，强转后面的类型。（这里就是拿getValue的对象强转
 * #9 // class java/lang/String）
 * #9 // class java/lang/String是从LocalVariableTypeTable里获取的
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