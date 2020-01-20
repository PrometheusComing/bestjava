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
 * @Description: lambda表达式
 * java.util.stream.Stream<T>下的方法类型
 *
 * public abstract Stream<T> filter(java.util.function.Predicate<? super T> predicate)
 *
 * public abstract <R> Stream<R> map(java.util.function.Function<? super T, ? extends R> mapper)
 *
 * public abstract java.util.Optional<T> reduce(java.util.function.BinaryOperator<T> accumulator)
 *
 * public abstract void forEach(java.util.function.Consumer<? super T> action)
 * // foreach操作后，继续操作
 * public abstract Stream<T> peek(java.util.function.Consumer<? super T> action)
 *
 *
 * accumulator是累加器，combiner组合器
 * BinaryOperator接口，可以看到reduce方法接受一个函数，这个函数有两个参数，第一个参数是上次函数执行的返回值（也称为中间结果），
 * 第二个参数是stream中的元素，这个函数把这两个值相加，得到的和会被赋值给下次执行这个函数的第一个参数。
 * 要注意的是：第一次执行的时候第一个参数的值是Stream的第一个元素，第二个参数是Stream的第二个元素
 * identity参数用来指定Stream循环的初始值。如果Stream为空，就直接返回该值。另一方面，该方法不会返回Optional，因为该方法不会出现null
 * Stream是支持并发操作的，为了避免竞争，对于reduce线程都会有独立的result，combiner的作用在于合并每个线程的result得到最终结果
 * public abstract java.util.Optional<T> reduce(java.util.function.BinaryOperator<T> accumulator)
 *
 * Optional是对null的一种优雅处理，get方法不安全，null时会抛出异常NoSuchElementException
 */
@Getter
@Setter
public class Lambda {

	private String myname;

	public static void main(String[] args) {
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

		Stream<String> stringStream = Stream.of("hah","xi","reng","reng");
		List<String> list = stringStream.distinct().sorted((m,n) -> (n.length() - m.length())).collect(Collectors.toList());


		//max这些方法的底层就是reduce
		Optional<String> optional = list.stream().peek(System.out::println).max(Comparator.comparingInt(String::length));
		optional.ifPresent(System.out::println);


		final Lambda lambda = new Lambda();
		lambda.setMyname("myself");
		boolean flag = list.stream().peek((t) -> {
			if (t.length() > 5) {
				lambda.setMyname("you");
			}
		}).anyMatch("reng"::equals);
		System.out.println(lambda.getMyname());
		System.out.println(flag);

	}
}
