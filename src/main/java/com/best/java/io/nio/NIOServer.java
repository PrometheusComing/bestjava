package com.best.java.io.nio;

import com.best.java.util.BytesWrapper;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * 本实例没有显示如何关闭
 */
public class NIOServer {

	private Selector selector;

	public static void main(String[] args) throws Exception {
		NIOServer nioServer = new NIOServer();
		nioServer.startServer();
	}

	public void startServer() throws IOException {
		// 选择器
		selector = Selector.open();
		// 通道channel
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);

		serverSocketChannel.bind(new InetSocketAddress(9001));
		// 在选择器上注册channel，一个选择器可以注册多个channel,有数据时会通知channel下的socket
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		for (; ; ) {
			// 阻塞方法，等待数据，返回SelectionKey（channel和选择器的绑定）
			System.out.println("server select ...");
			selector.select();
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator<SelectionKey> i = readyKeys.iterator();
			while (i.hasNext()) {
				SelectionKey sk = i.next();
				// 移除当前SelectionKey
				i.remove();
				// 客户端请求连接
				if (sk.isAcceptable()) {
					doAccept(sk);
				} else if (sk.isValid() && sk.isReadable()) {
					// 客户端写的数据，服务端读
					doRead(sk);
				} else if (sk.isValid() && sk.isWritable()) {
					// 服务端写数据，客户端去读
					doWrite(sk);
				}

			}

		}

	}

	private void doAccept(SelectionKey sk) {
		System.out.println("server doAccept ...");
		ServerSocketChannel ssc = (ServerSocketChannel) sk.channel();
		// 获取代表客户端的channel
		SocketChannel clientChannel;
		try {
			// 与客户端建立连接
			clientChannel = ssc.accept();
			clientChannel.configureBlocking(false);
			// 注册该channel到read，也就是如果客户端向服务端写数据了，
			// 那么服务端的clientChannel就会收到读客户端的要求，所以是注册read
			clientChannel.register(selector, SelectionKey.OP_READ);
			InetAddress clientAddress = clientChannel.socket().getInetAddress();
			System.out.println("get connect from " + clientAddress.getHostName());
		} catch (IOException e) {
			System.out.println("faile to accept new client");
			e.printStackTrace();
		}
	}

	private void doRead(SelectionKey sk) {
		System.out.println("server doRead ...");
		SocketChannel sc = (SocketChannel) sk.channel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(8);
		BytesWrapper bytesWrapper = new BytesWrapper();
		byte[] bytes;
		try {
			// 通道中没数据，返回0
			// 通道已到达流的末尾，返回-1
			// byteBuffer满了，read也会返回0
			int readBytesSize = sc.read(byteBuffer);
			while (readBytesSize > 0) {
				System.out.println("readBytesSize = " + readBytesSize);
				bytes = new byte[readBytesSize];
				byteBuffer.flip();
				byteBuffer.get(bytes);
				bytesWrapper.append(bytes);
				// 必须clean，否则byteBuffer满了，read也会返回0
				// 比如客户端发来17字节，byteBuffer只有8字节，如果不clean的话，byteBuffer读满了
				// read返回0，结束循环，那么通道里还有9字节，会触发下次读事件，再执行一次doRead方法，直到读完
				// 使用clean后，byteBuffer回归初始，可以继续从通道读字节
				byteBuffer.clear();
				readBytesSize = sc.read(byteBuffer);
			}
			System.out.println("server get msg: " + new String(bytesWrapper.builder()) + " from " + sc.socket().getInetAddress().getHostName());
			System.out.print("输入消息：");
			Scanner scanner = new Scanner(System.in);
			String msg = scanner.next();

			// 注册写事件，也可以仿照NIOClient里doConnect那样，直接写
			sk.interestOps(
					sk.interestOps() | SelectionKey.OP_WRITE);
			// 把消息附带上
			sk.attach(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doWrite(SelectionKey sk) {
		try {
			System.out.println("server doWrite ...");
			SocketChannel channel = (SocketChannel) sk.channel();
			String msg = (String) sk.attachment();

			ByteBuffer byteBuffer = ByteBuffer.allocate(8);

			// 开始写
			int length = byteBuffer.capacity();
			// 要写出的内容
			byte[] results = msg.getBytes();
			// 偏移量
			int offset = 0;
			// 剩余要写出的内容的长度
			int remainLength = results.length;
			while (remainLength > 0) {
				// 剩余的长度比byteBuffer大，则写出byteBuffer的长度，否则写出剩余长度
				System.out.println("remainLength = " + remainLength);
				// 从results数组的offset开始，截取Math.min(remainLength, length)的长度，放入byteBuffer
				byteBuffer.put(results, offset, Math.min(remainLength, length));
				byteBuffer.flip();
				// 判断posion 和limit之间有没有元素
				while (byteBuffer.hasRemaining()) {
					// 如果网络不好，这里的write返回的写入的长度就是0，导致这里变成无线循环
					int len = channel.write(byteBuffer);
					if (len < 0) {
						throw new EOFException();
					}
					// 一般情况无需注册写事件，没啥用
					if (len == 0) {
						break;
					}
				}
				byteBuffer.clear();
				// 偏移量步进
				offset = offset + length;
				remainLength = remainLength - length;
			}
			//发送完了就取消写事件，否则下次还会进入该分支
			sk.interestOps(sk.interestOps() & ~SelectionKey.OP_WRITE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
