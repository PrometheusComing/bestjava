package com.best.java.hashmap;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @Author: xjxu3
 * @Date: 2019/10/18 15:36
 * @Description:
 */
public class HashMapTest {

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	public static void main(String[] args) {
//		HashMap<String, String> map = new HashMap<>(2,1.0f);
//		map.put("Aa","Aa");
//		map.put("BB","BB");
//
//		map.put("345","BB");
//
//		Class cls = map.getClass();
//		try {
//			Field field = cls.getDeclaredField("threshold");
//			field.setAccessible(true);
//			try {
//				System.out.println(field.get(map));
//				System.out.println(field.get(map));
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			}
//		} catch (NoSuchFieldException e) {
//			e.printStackTrace();
//		}

		HashMap<HashMapTest, String> map = new HashMap<>();
		for (int i =0;i < 20;i ++ ) {
			map.put(new HashMapTest(),"123");
		}


		Class cls = map.getClass();
		try {
			Field field = cls.getDeclaredField("threshold");
			field.setAccessible(true);
			try {
				System.out.println(field.get(map));
				System.out.println(field.get(map));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}


	}
}
