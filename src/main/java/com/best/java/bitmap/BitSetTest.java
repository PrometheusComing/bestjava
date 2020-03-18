package com.best.java.bitmap;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: xjxu3
 * @Date: 2020/3/9 11:34
 * @Description:  bitmap的java实现
 */
public class BitSetTest {
	public static void main(String[] args) {
		AtomicInteger i = new AtomicInteger(10);
		i.incrementAndGet();

		BitSet bitSet = new BitSet();
		bitSet.set(0);
		System.out.println(bitSet.size());
		bitSet.set(1);
		System.out.println(bitSet.size());
		bitSet.set(70);
		bitSet.set(70);
		System.out.println(bitSet.size());
		System.out.println(bitSet.get(0));
		System.out.println(bitSet.get(1));
		System.out.println(bitSet.get(70));
		System.out.println(bitSet.get(90));
		bitSet = new BitSet(90);
		System.out.println(bitSet.size());
	}
}
