package com.best.java.hashmap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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
//		test 1
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



//		test 2
//		HashMap<HashMapTest, String> map = new HashMap<>(10);
//		for (int i =0;i < 1;i ++ ) {
//			map.put(new HashMapTest(),"123");
//		}
//
//
//		Class cls = map.getClass();
//
//		try {
//			Field field = cls.getDeclaredField("table");
//			field.setAccessible(true);
//			try {
//				Object[] objects = (Object[]) field.get(map);
//				System.out.println(objects.length);
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			}
//		} catch (NoSuchFieldException e) {
//			e.printStackTrace();
//		}


		Map<String, String> map = new HashMap<>();
		map.put("1", null);
		System.out.println(map.get("1"));
//		map.putIfAbsent("1", null);
		map.put("1", "1");
		System.out.println(map.get("1"));
		map.putIfAbsent("1", "2");
		System.out.println(map.get("1"));
		map.put("1", null);
		System.out.println(map.get("1"));
		// 不会覆盖旧值，但是旧值是null，依旧会覆盖
		map.putIfAbsent("1", "2");
		System.out.println(map.get("1"));
	}
}
