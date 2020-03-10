package com.best.java.list;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: xjxu3
 * @Date: 2020/1/27 17:08
 * @Description:
 */
public class ListTest {
	//	public static void main(String[] args) {
//		List<String> lists = new ArrayList<>();
//		lists.add("aaa");
//		lists.add("aaa");
//		lists.add("bbb");
//		lists.add("ccc");
//		lists.add("ddd");
//		for (int i = 0; i < lists.size(); i++) {
//			if ("aaa".equals(lists.get(i))) {
//				lists.remove(i);
//			}
//		}
//		lists.forEach(System.out::println);
//	}
	public static void main(String[] args) {
		Set<String> set = new HashSet<>();
		System.out.println(set.add("123"));
		System.out.println(set.add("123"));
//		CopyOnWriteArrayList
//		Set<Short> s = new HashSet<Short>();
//		for (Short i = 0; i < 100; i++) {
//			// 存的是short
//			s.add(i);
//			// 移除的是int
//			s.remove( i - 1);
//		}
//		System.out.println(s.size());
	}

}
