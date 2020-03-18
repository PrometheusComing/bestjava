package com.best.java.pattern.builder;

/**
 * @Author: xjxu3
 * @Date: 2019/10/11 16:22
 * @Description:
 */
public class ImproveComSon extends ImproveComputer {

	private String myself;

	public ImproveComSon(String cpu, String screen, String memory, String mainboard, String myself) {
		super(cpu, screen, memory, mainboard);
		this.myself = myself;
	}
}
