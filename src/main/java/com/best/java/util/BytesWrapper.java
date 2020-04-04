package com.best.java.util;

/**
 * @Author: xjxu3
 * @Date: 2020/4/4 10:28
 * @Description: 字节数组包装类
 */
public class BytesWrapper {

	private byte[] bytes;

	public BytesWrapper() {
	}

	public BytesWrapper(byte a) {
		this.bytes = new byte[]{a};
	}

	public BytesWrapper(byte[] bytes) {
		this.bytes = bytes;
	}

	public BytesWrapper append(byte[] bytes) {
		this.bytes = BytesUtil.append(this.bytes, bytes);
		return this;
	}

	public BytesWrapper append(byte a) {
		this.bytes = BytesUtil.append(this.bytes, a);
		return this;
	}

	public byte[] builder() {
		return this.bytes;
	}
}
