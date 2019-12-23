package com.best.java.aloader;

/**
 * @Author: xjxu3
 * @Date: 2019/12/20 17:56
 * @Description:
 */
public class BEntity extends AEntity {

	static {
		System.out.println("BEntity class load : " + BEntity.class.getClassLoader());
	}

	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
