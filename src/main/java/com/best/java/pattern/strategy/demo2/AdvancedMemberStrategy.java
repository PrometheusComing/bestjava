package com.best.java.pattern.strategy.demo2;

/**
 * @Author: xjxu3
 * @Date: 2020/5/19 15:23
 * @Description:
 */
public class AdvancedMemberStrategy implements MemberStrategy {

	@Override
	public double calcPrice(double booksPrice) {

		System.out.println("对于高级会员的折扣为20%");
		return booksPrice * 0.8;
	}
}
