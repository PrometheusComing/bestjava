package com.best.java.pattern.strategy.demo2;

/**
 * @Author: xjxu3
 * @Date: 2020/5/19 15:21
 * @Description:
 */
public class PrimaryMemberStrategy implements MemberStrategy {

	@Override
	public double calcPrice(double booksPrice) {
		System.out.println("对于初级会员的没有折扣");
		return booksPrice;
	}

}
