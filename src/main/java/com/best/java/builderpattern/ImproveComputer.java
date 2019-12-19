package com.best.java.builderpattern;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: xjxu3
 * @Date: 2019/10/10 15:48
 * @Description: 使用建造者的计算机类
 */
@Getter
@Setter
public class ImproveComputer {
	private String cpu;
	private String screen;
	private String memory;
	private String mainboard;

//	public String getCpu() {
//		return cpu;
//	}
//
//	public void setCpu(String cpu) {
//		this.cpu = cpu;
//	}
//
//	public String getScreen() {
//		return screen;
//	}
//
//	public void setScreen(String screen) {
//		this.screen = screen;
//	}
//
//	public String getMemory() {
//		return memory;
//	}
//
//	public void setMemory(String memory) {
//		this.memory = memory;
//	}
//
//	public String getMainboard() {
//		return mainboard;
//	}
//
//	public void setMainboard(String mainboard) {
//		this.mainboard = mainboard;
//	}

//	public ImproveComputer(Builder builder) {
//		cpu = builder.cpu;
//		screen = builder.screen;
//		memory = builder.memory;
//		mainboard = builder.mainboard;
//	}

	public ImproveComputer(String cpu, String screen, String memory, String mainboard) {
		this.cpu = cpu;
		this.screen = screen;
		this.memory = memory;
		this.mainboard = mainboard;
	}

	static final class Builder {
		private String cpu;
		private String screen;
		private String memory;
		private String mainboard;

		Builder() {}

		Builder cpu(String val) {
			cpu = val;
			return this;
		}
		Builder screen(String val) {
			screen = val;
			return this;
		}
		Builder memory(String val) {
			memory = val;
			return this;
		}
		Builder mainboard(String val) {
			mainboard = val;
			return this;
		}
		ImproveComputer build() {
//			return new  ImproveComputer(this);
			return new  ImproveComputer(this.cpu,this.screen,this.memory,this.mainboard);
		}
	}
}
