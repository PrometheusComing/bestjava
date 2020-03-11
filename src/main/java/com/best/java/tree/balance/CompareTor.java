package com.best.java.tree.balance;

import java.util.Comparator;

/**
 * @Author: xjxu3
 * @Date: 2020/3/11 10:07
 * @Description: 常用作匿名类
 */
public abstract class CompareTor<T> implements Comparator<T>  {

	@Override
	public int compare(T o1, T o2) {
		return 0;
	}
}
