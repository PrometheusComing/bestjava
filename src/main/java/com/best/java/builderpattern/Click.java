package com.best.java.builderpattern;

/**
 * @Author: xjxu3
 * @Date: 2019/10/10 15:51
 * @Description:
 */
public class Click {
	public static void main(String[] args) {
		// 非 Builder 模式
		Computer computer = new Computer("cpu", "screen", "memory", "mainboard");
		// Builder 模式
		ImproveComputer improveComputer = new ImproveComputer.Builder()
				.cpu("cpu")
				.screen("screen")
				.memory("memory")
				.mainboard("mainboard").build();
	}
}
