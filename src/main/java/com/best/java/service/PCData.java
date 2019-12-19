package com.best.java.service;

public class PCData {
	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	private int data;

	PCData(int data) {
		this.data = data;
	}

	PCData(String data) {
		this.data = Integer.parseInt(data);
	}
}
