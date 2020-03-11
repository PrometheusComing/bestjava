package com.best.java.tree.balance;

/**
 * @Author: xjxu3
 * @Date: 2020/3/11 10:05
 * @Description: 自己与其他对象比较
 */
public abstract class CompareAble<T> implements Comparable<T> {
	public abstract int compareTo(T o);
}
