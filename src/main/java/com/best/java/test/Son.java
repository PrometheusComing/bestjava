package com.best.java.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: xjxu3
 * @Date: 2019/9/12 23:56
 * @Description:
 */
public class Son {
	public static void main(String[] args) {
		ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap();
		Integer integer = new Integer(11);

		HashMap<String,String> hashMap = new HashMap();
		concurrentHashMap.put(null, null);
//		concurrentHashMap.get(null);
		hashMap.put(null, null);
		hashMap.put("1", null);
		System.out.println(hashMap.get(2));
//		try {
//			String[] strings = new String[]{"123"};
//			List list = Arrays.asList(strings);
//			System.out.println(list.get(3));
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//
	}
}
