package com.best.java.lambda;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: xjxu3
 * @Date: 2019/10/8 15:19
 * @Description:
 */
@Getter
@Setter
public class Lambda {
	private String myname;

	public static void main(String[] args) {
		Map<Object, Object> map = new HashMap<>();
		map.put("11", "2");
		map.put(12, "nih");
		System.out.println(map.get("11"));
		System.out.println(map.get(12));
		List<String> listInner1 = new ArrayList<String>() {
			{
				add("abcd");
				add("abcdefg");
				add("abcdcba");
				add("abcddcbba");
			}
		};
		List<String> listInner2 = new ArrayList<String>() {
			{
				add("abcd");
				add("pkore");
				add("pkower");
				add("srcpko");

			}
		};
		List<String> listInner3 = new ArrayList<String>() {
			{
				add("w32");
				add("wwq");
				add("你好啊");
			}
		};
		List<String> listInner4 = new ArrayList<>();
		List<List<String>> listOutter = new ArrayList<List<String>>() {
			{
				add(listInner1);
				add(listInner2);
				add(listInner3);
				add(listInner4);
				add(null);
			}
		};
		List<String> newList = listOutter.stream().filter(t -> t != null && t.size() > 0).map(t -> {
			Optional<String> optional = t.stream().reduce(String::concat);
			return optional.orElse("isnull");
		}).collect(Collectors.toList());
		System.out.println(newList);
		List<String> newList2 = listOutter.stream().filter(t -> t != null && t.size() > 0).flatMap(Collection::stream).distinct().collect(Collectors.toList());
		System.out.println(newList2);

//		Stream<String> stringStream = Stream.of("hah","xi","reng","reng");
//		List<String> list = stringStream.distinct().sorted((m,n) -> (n.length() - m.length())).collect(Collectors.toList());
//		list.forEach(System.out::println);
//		Optional<String> optional = list.stream().max(Comparator.comparingInt(String::length));
//		optional.ifPresent(System.out::println);
//		final Lambda lambda = new Lambda();
//		lambda.setMyname("myself");
//		list.stream().forEach((t) -> {
//			if (t.length() > 5) {
//				lambda.setMyname("you");
//			}
//		});
//		System.out.println(lambda.getMyname());
//		boolean flag = list.stream().anyMatch("reng"::equals);
//		System.out.println(flag);

	}
}
