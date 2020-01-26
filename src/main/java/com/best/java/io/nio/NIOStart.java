package com.best.java.io.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class NIOStart {

	private Selector selector;
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private Map<Socket,Long> time_stat = new HashMap<>();

	public void startServer() throws IOException {
		// 选择器
		selector = SelectorProvider.provider().openSelector();
		// 通道channel
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);

		serverSocketChannel.bind(new InetSocketAddress(9001));
		// 在选择器上注册channel，一个选择器可以注册多个channel,有数据时会通知channel下的socket
		SelectionKey selectionKey = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

		for(;;) {
			// 阻塞方法，等待数据，返回SelectionKey（channel和选择器的绑定）
			selector.select();
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator<SelectionKey> i = readyKeys.iterator();
			long e = 0;
			while (i.hasNext()) {
				SelectionKey sk = i.next();
				i.remove();
				if (sk.isAcceptable()) {
					doAccept(sk);
				} else if (sk.isValid() && sk.isReadable()) {
					if (!time_stat.containsKey(((SocketChannel)sk.channel()).socket())) {
						time_stat.put(((SocketChannel)sk.channel()).socket(),System.currentTimeMillis());
					}
					doRead(sk);
				} else if (sk.isValid() && sk.isWritable()) {
					doWrite(sk);
					e = System.currentTimeMillis();
					long b = time_stat.remove(((SocketChannel)sk.channel()).socket());
					System.out.println("spend :" + (e - b) + "ms");
				}

			}

		}

	}
	private void doAccept(SelectionKey sk) {
		ServerSocketChannel ssc = (ServerSocketChannel)sk.channel();
		SocketChannel clientChannel;
		try {
			clientChannel = ssc.accept();
			clientChannel.configureBlocking(false);
			// 注册该通道到read
			SelectionKey clientKey = clientChannel.register(selector,SelectionKey.OP_READ);
			EchoClient echoClient = new EchoClient();
			clientKey.attach(echoClient);
			InetAddress clientAddress = clientChannel.socket().getInetAddress();
			System.out.println("get connect from " + clientAddress.getHostName());
		} catch (IOException e) {
			System.out.println("faile to accept new client");
			e.printStackTrace();
		}
	}
	private void doRead(SelectionKey sk) {
		SocketChannel  sc = (SocketChannel) sk.channel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(8192);
		int len;
		try {
			len = sc.read(byteBuffer);
			if (len < 0) {
//				disconnect(sk);
				return;
			}
		} catch (IOException e) {
			System.out.println("faile to read from client");
			e.printStackTrace();
//			disconnect(sk);
			return;
		}
		byteBuffer.flip();
		executorService.execute(new Handle(sk,byteBuffer));
	}
	private void doWrite(SelectionKey sk) {
		SocketChannel sc = (SocketChannel)sk.channel();
		EchoClient echoClient = (EchoClient)sk.attachment();
		LinkedList<ByteBuffer> linkedList = echoClient.getOutputQueue();
		ByteBuffer bb = linkedList.getLast();
		try {
			int len = sc.write(bb);
			if (len == -1) {
				return;
			}
			if (bb.remaining() == 0) {
				linkedList.removeLast();
			}
		} catch (IOException e) {
			System.out.println("faile to write to client");
		}
		if (linkedList.size() == 0) {
			sk.interestOps(SelectionKey.OP_READ);
		}
	}
}
