package com.best.java.pattern.command;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @Author: xjxu3
 * @Date: 2019/10/10 15:26
 * @Description: builder会自动生成一个全参的构造器，所以子类的builder里要引用父类的构造器才行
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class TVMachine implements Machine {

	private String brand;

	@Builder.Default
	private int price = 10;

	private String producePlace;

	@Override
	public void open() {
		System.out.println("TV is on");
	}

	@Override
	public void close() {
		System.out.println("TV is down");
	}

	@Override
	public void switchIt() {
		System.out.println("TV channel has changed");
	}
}
