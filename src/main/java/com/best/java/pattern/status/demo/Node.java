package com.best.java.pattern.status.demo;

/**
 * @Author: xjxu3
 * @Date: 2020/5/18 21:06
 * @Description:
 */
public abstract class Node {
	private static String name; //当前节点名称

	//节点跳转
	public abstract void nodeHandle(FlowContext context);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
