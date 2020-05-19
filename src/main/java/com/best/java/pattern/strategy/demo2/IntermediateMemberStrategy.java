package com.best.java.pattern.strategy.demo2;

/**
 * @Author: xjxu3
 * @Date: 2020/5/19 15:22
 * @Description:
 */
public class IntermediateMemberStrategy implements MemberStrategy {

	@Override
	public double calcPrice(double booksPrice) {

		System.out.println("对于中级会员的折扣为10%");
		return booksPrice * 0.9;
	}

}