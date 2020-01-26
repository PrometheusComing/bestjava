package com.best.java.io.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class Handle implements Runnable {

	SelectionKey sk;
	ByteBuffer bb;
	Selector selector;
	Handle(SelectionKey sk,ByteBuffer bb) {
		this.bb = bb;
		this.sk = sk;
	}
	@Override
	public void run() {
		EchoClient echoClient = (EchoClient)sk.attachment();
		echoClient.enQueue(bb);
		sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		selector.wakeup();
	}
}
