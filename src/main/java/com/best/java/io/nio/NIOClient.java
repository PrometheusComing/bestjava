package com.best.java.io.nio;

import com.best.java.util.BytesWrapper;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
/**
 * 本实例没有显示如何关闭，很简单，自己搞去。
 *
 * 人烧水这件事情。我将它对应到netty的一些组件上，首先人不傻，
 * 懂得在水没开的时候我可以做其他的事。（非阻塞）再就是水开了会报警。（异步）
 * 人听到报警就去把炉子关了。（事件驱动）
 * selector就是炉子上的传感器，监视水温。回到话题，selector搭配非阻塞io，
 * 你可以想象上面过程中如果人是个傻子，烧水的时候就要一直站在边上等着，那么其他组件是不是就显得多余
 *
 *
 */
public class NIOClient {

	private Selector selector;

	public static void main(String[] args) throws Exception {
		NIOClient nioClient = new NIOClient();
		nioClient.start();
	}

	public void start() throws IOException {

		// 选择器
		this.selector = Selector.open();

		// 获得一个Socket通道
		SocketChannel channel = SocketChannel.open();
		// 设置通道为非阻塞
		channel.configureBlocking(false);

		// 尝试直接连接
		if (channel.connect(new InetSocketAddress("127.0.0.1", 9001))) {
			System.out.println("link success...");
			channel.register(selector, SelectionKey.OP_READ);
			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
			byteBuffer.put("服务端，hello".getBytes());
			byteBuffer.flip();
			while (byteBuffer.hasRemaining()) {
				channel.write(byteBuffer);
			}
		} else {
			//为该通道注册SelectionKey.OP_CONNECT事件。
			channel.register(selector, SelectionKey.OP_CONNECT);
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (; ; ) {
			// 阻塞方法，等待数据，返回SelectionKey（channel和选择器的绑定）
			System.out.println("client select...");
			selector.select();
			// 获取所有已就绪key
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator<SelectionKey> i = readyKeys.iterator();
			while (i.hasNext()) {
				SelectionKey sk = i.next();
				i.remove();
				// 客户端请求连接
				if (sk.isConnectable()) {
					doConnect(sk);
				} else if (sk.isValid() && sk.isReadable()) {
					// 服务端写的数据，客户端读
					doRead(sk);
				} else if (sk.isValid() && sk.isWritable()) {
					doWrite(sk);
				}

			}

		}

	}

	private void doConnect(SelectionKey sk) {
		try {
			System.out.println("client doConnect...");
			SocketChannel channel = (SocketChannel) sk.channel();
			// 如果正在连接，则完成连接
			if (channel.isConnectionPending()) {
				channel.finishConnect();
			}
			// 设置成非阻塞
			channel.configureBlocking(false);

			//在这里可以给服务端发送信息
			//在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
			channel.register(this.selector, SelectionKey.OP_READ);
			ByteBuffer byteBuffer = ByteBuffer.allocate(8);

			// 开始写
			int length = byteBuffer.capacity();
			// 要写出的内容
			byte[] results = "服务端，hello，这里是你爹的地盘，其实我只是在凑字数而已，看把你吓得".getBytes();
			// 偏移量
			int offset = 0;
			// 剩余要写出的内容的长度
			int remainLength = results.length;
			while (remainLength > 0) {
				// 剩余的长度比byteBuffer大，则写出byteBuffer的长度，否则写出剩余长度
				System.out.println("remainLength = " + remainLength);
				// 从results数组的offset开始，截取Math.min(remainLength, length)的长度，放入byteBuffer
				byteBuffer.put(results,offset, Math.min(remainLength, length));
				byteBuffer.flip();
				// 判断posion 和limit之间有没有元素
				while (byteBuffer.hasRemaining()) {
					// 如果网络不好，这里的write返回的写入的长度就是0，导致这里变成无线循环
					int len = channel.write(byteBuffer);
					if (len < 0){
						throw new EOFException();
					}
					// 一般情况无需注册写事件，没啥用
					if (len == 0) {
						// 在原有基础上新增写事件，sk.interestOps()返回旧的兴趣
						sk.interestOps(
								sk.interestOps() | SelectionKey.OP_WRITE);
						// 因为一般都是new一个线程处理，主线程肯定又select了，所以在此处唤醒
						// 等当前通道可写的时候，继续写出去，后续还需要取消写事件
						selector.wakeup();
						break;
					}
				}
//				try {
					// 这里客户端故意停2秒再继续下一次写入，也就造成通道里会有2秒是无数据可以读的
					// 那么服务端就会接收这次的数据，处理，随后继续监听通道
					// 客户端2秒后，会继续往通道写数据，服务端再次监听到数据，继续接收
					// 结果由于results被分割发送了，服务端直接使用string还原字节数组，可能就乱码了
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				byteBuffer.clear();
				// 偏移量步进
				offset = offset + length;
				remainLength = remainLength - length;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doRead(SelectionKey sk) {
		System.out.println("client doRead...");
		SocketChannel sc = (SocketChannel) sk.channel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(8);
		BytesWrapper bytesWrapper = new BytesWrapper();
		byte[] bytes;
		try {
			int readBytesSize = sc.read(byteBuffer);
			while (readBytesSize > 0) {
				System.out.println("readBytesSize = " + readBytesSize);
				bytes = new byte[readBytesSize];
				byteBuffer.flip();
				byteBuffer.get(bytes);
				bytesWrapper.append(bytes);
				byteBuffer.clear();
				readBytesSize = sc.read(byteBuffer);
			}
			System.out.println("client get msg: " + new String(bytesWrapper.builder()) + " from " + sc.socket().getInetAddress().getHostName());
			System.out.print("输入消息：");
			Scanner scanner = new Scanner(System.in);
			String msg = scanner.next();
			byteBuffer.clear();

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
				byteBuffer.put(results,offset, Math.min(remainLength, length));
				byteBuffer.flip();
				// 判断posion 和limit之间有没有元素
				while (byteBuffer.hasRemaining()) {
					sc.write(byteBuffer);
				}
				byteBuffer.clear();
				// 偏移量步进
				offset = offset + length;
				remainLength = remainLength - length;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void doWrite(SelectionKey sk) {
		System.out.println("client doWrite...");

		SocketChannel sc = (SocketChannel) sk.channel();
		System.out.println(111);
	}
}
