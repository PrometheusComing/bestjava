package com.best.java.nioSocketTest;

import java.nio.ByteBuffer;
import java.util.LinkedList;

public class EchoClient {
	private LinkedList<ByteBuffer> outq;
	EchoClient() {
		outq = new LinkedList<>();
	}
	public LinkedList<ByteBuffer> getOutputQueue () {
		return outq;
	}
	public void enQueue(ByteBuffer byteBuffer) {
		outq.add(byteBuffer);
	}
}
