package com.best.java.pattern.builder;

/**
 * @Author: xjxu3
 * @Date: 2019/10/10 15:47
 * @Description: 普通计算机类
 */
public class Computer {
	private String cpu;
	private String screen;
	private String memory;
	private String mainboard;
	public Computer(String cpu, String screen, String memory, String mainboard) {
		this.cpu = cpu;
		this.screen = screen;
		this.memory = memory;
		this.mainboard = mainboard;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getMainboard() {
		return mainboard;
	}

	public void setMainboard(String mainboard) {
		this.mainboard = mainboard;
	}
}
