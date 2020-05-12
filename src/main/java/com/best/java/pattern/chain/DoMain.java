package com.best.java.pattern.chain;

/**
 * @Author: xjxu3
 * @Date: 2020/5/12 15:30
 * @Description:
 */
public class DoMain {
	public static void main(String[] args) {
		ChainPipeline pipeline = new ChainPipeline();
		pipeline.addHandler(new CarHeadHandler())
				.addHandler(new CarBodyHandler())
				.addHandler(new CarTailHandler())
				.execute();
	}
}
