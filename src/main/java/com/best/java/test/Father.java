package com.best.java.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @Author: xjxu3
 * @Date: 2019/9/12 23:55
 * @Description:
 */
public class Father {
	private String name;
	private Integer n = 0;
    private static void set(){
		System.out.println("11");
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	public static void main(String[] args) {

		ArrayList<Date> list=new ArrayList<Date>();
		list.add(new Date());
		Date myDate=list.get(0);
	}
}
