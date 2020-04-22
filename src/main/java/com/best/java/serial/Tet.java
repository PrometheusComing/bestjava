package com.best.java.serial;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @Author: xjxu3
 * @Date: 2020/4/15 15:11
 * @Description:  序列化测试
 */
public class Tet {
	class inner{
		private int h;
	}
	public static void main(String[] args) throws Exception{
//		GeneratedSerializationConstructorAccessor
		Constructor constructor = Demo.class.getDeclaredConstructor();
		Demo.class.newInstance();
		constructor.newInstance();
		Demo.print();
//		Field field = constructor.getClass().getDeclaredField("clazz");
//		Field field2 = constructor.getClass().getDeclaredField("root");
//		field.setAccessible(true);
//		field2.setAccessible(true);
//		field.set(constructor,Papa.class);
//		field2.set(constructor,null);
//		Demo demo = (Demo) constructor.newInstance();
//		System.out.println(123);
//		Demo d = new Demo();
//		d.setMa("ma");
//		d.setNa("na");
//		d.setNage(10);
//		ObjectOutputStream objectOutputStream = new ObjectOutputStream
//				(new FileOutputStream(new File("C:\\Users\\prometheus\\Desktop\\1.txt")));
//		objectOutputStream.writeObject(d);
//		objectOutputStream.close();
//////
//		ObjectInputStream objectInputStream = new ObjectInputStream
//				(new FileInputStream(new File("C:\\Users\\prometheus\\Desktop\\1.txt")));
//		Demo demo = (Demo) objectInputStream.readObject();
//		System.out.println(demo.getMa());
//		objectInputStream.close();
//		d.getI();
	}
}


class Papa {
	private String p;

	public static void print() {
		System.out.println("print static");
	}

	public Papa() {
		System.out.println("papa");
	}
}
abstract class Demo extends Papa implements Serializable {
	private static final long serialVersionUID = 1321973566122198279L;
	private String na;
	private String ma;
	private int nage;

//	public int getI(Object ... initargs) {
//		return 100;
//	}

	public Demo() {
		System.out.println("fuck");
	}

	public String getNa() {
		return na;
	}

	public void setNa(String na) {
		this.na = na;
	}

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public int getNage() {
		return nage;
	}

	public void setNage(int nage) {
		this.nage = nage;
	}
}
