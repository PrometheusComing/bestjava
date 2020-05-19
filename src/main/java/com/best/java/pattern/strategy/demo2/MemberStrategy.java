package com.best.java.pattern.strategy.demo2;

/**
 * @Author: xjxu3
 * @Date: 2020/5/19 15:21
 * @Description:
 */
public interface MemberStrategy {
	/**
	 * 计算图书的价格
	 * @param booksPrice    图书的原价
	 * @return    计算出打折后的价格
	 */
	double calcPrice(double booksPrice);
}
