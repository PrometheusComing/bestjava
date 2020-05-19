package com.best.java.pattern.strategy.demo2;

/**
 * @Author: xjxu3
 * @Date: 2020/5/19 15:24
 * @Description:
 * 和命令模式很像，但是偏重点不同，策略模式偏重于一系列算法的封装，并且他们可以替换，命令模式偏重于实际执行者和调用者的解耦
 *
 * 策略模式是通过不同的算法做同一件事情
 * 而命令模式则是通过不同的命令做不同的事情
 */
public class Price {
	//持有一个具体的策略对象
	private MemberStrategy strategy;

	/**
	 * 构造函数，传入一个具体的策略对象
	 *
	 * @param strategy 具体的策略对象
	 */
	public Price(MemberStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * 计算图书的价格
	 *
	 * @param booksPrice 图书的原价
	 * @return 计算出打折后的价格
	 */
	public double quote(double booksPrice) {
		return this.strategy.calcPrice(booksPrice);
	}

	public static void main(String[] args) {
		//选择并创建需要使用的策略对象
		MemberStrategy strategy = new AdvancedMemberStrategy();
		//创建环境
		Price price = new Price(strategy);
		//计算价格
		double quote = price.quote(300);
		System.out.println("图书的最终价格为：" + quote);
	}
}
