package com.best.java.annotation;

/**
 * @Author: xjxu3
 * @Date: 2020/3/21 11:12
 * @Description:
 */
public enum EnumOperation {

	ADD("增加"),
	DELETE("删除"),
	UPDATE("修改"),
	SELECT("查询"),
	OTHER("其他");

	private String type;

	EnumOperation(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
