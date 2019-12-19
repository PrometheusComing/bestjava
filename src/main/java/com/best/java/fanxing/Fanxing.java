package com.best.java.fanxing;

import com.best.java.object.Intf;

import javax.swing.text.DateFormatter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: xjxu3
 * @Date: 2019/10/12 10:00
 * @Description: 泛型相关
 */
public class Fanxing<T> {

	private T t;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	//引发命名冲突，编译后依旧是equals(Object value)
//	public boolean equals(T value) {
//		return true;
//	}

	public static void main(String[] args) {
//		List<String> list = new ArrayList<>();
//		list.add("123");
//		if (list instanceof ArrayList<?>) {
//			DateInter dateInter = new DateInter();
//			dateInter.setValue(new Date());
//			System.out.println(dateInter.getValue());
//		}


		List<String> list = new ArrayList<String>() {{
			add("123");
		}};
		Class cla = list.getClass();
		try {
			Method method = cla.getMethod("add", Object.class);
			method.invoke(list, 12);
			list.stream().forEach(System.out::println);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		Fanxing<Integer> fanxing = new Fanxing<>();
		fanxing.setT(100);
		Integer i = fanxing.getT("12","34");
		System.out.println(i);
		System.out.println(fanxing.getString(12));
		System.out.println(fanxing.<String>getString(null));
		System.out.println(fanxing.getString("haha"));
		System.out.println(fanxing.getString(fanxing));
		System.out.println(fanxing.getString2(15));
		System.out.println(fanxing.getString3(fanxing));
	}

	public <K,V> T getT(K k,V v) {
		return t;
	}

	// 这里所有的T和类的T无关
	public <T> T getString(T m) {
		if (m == null) {
			return null;
		} else {
			return m;
		}
	}
	//这里所有的T和类的T一样
	public T getString2(T m) {
		if (m == null) {
			return null;
		} else {
			return m;
		}
	}

	public <K> K getString3(Fanxing<K> m) {
		K k = m.getT();
		return k;
	}
}
