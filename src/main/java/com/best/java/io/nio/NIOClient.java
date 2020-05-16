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
 * ps:为什么nio要设置socketChannel为非阻塞模式？
 * 1.epoll的ET模式下是必须使用非阻塞IO的。
 * ET模式指的是当数据从无到有时，才通知该fd。数据读不完，也不会再次通知，
 * 所以read时一定要采用循环的方式一直读到read函数返回-1为止。此时采用阻塞的read，那么就阻塞了整个线程
 * 2.epoll的LT模式其实是支持阻塞IO的，可以通过for循环里每次都先调用一次select后再read一次，可是这样的效率很低下。这里如果是一次select后循环
 * 多次read的话（上面1的方式），就有可能阻塞整个线程
 * 3.多个进程或者线程通过 select 或者 epoll 监听一个 listen socket，当有一个新连接完成三次握手之后，所有进程都会通过 select
 * 或者 epoll 被唤醒，但是最终只有一个进程或者线程 accept 到这个新连接，若是采用了阻塞 I/O，没有accept到连接的进程或者线程就 block 住了
 *
 *
 * epoll对文件描述符的操作有两种模式：LT（level trigger）和ET（edge trigger）。LT模式是默认模式，LT模式与ET模式的区别如下：
 * 　　LT模式：当epoll_wait检测到描述符事件发生并将此事件通知应用程序，应用程序可以不立即处理该事件。
 * 			下次调用epoll_wait时，会再次响应应用程序并通知此事件。（LT(level triggered)是缺省的工作方式，
 * 			并且同时支持block和no-block socket.在这种做法中，内核告诉你一个文件描述符是否就绪了，
 * 			然后你可以对这个就绪的fd进行IO操作。如果你不作任何操作，内核还是会继续通知你的）
 * 　　ET模式：当epoll_wait检测到描述符事件发生并将此事件通知应用程序，应用程序必须立即处理该事件。
 * 			 如果不处理，下次调用epoll_wait时，不会再次响应应用程序并通知此事件（ET(edge-triggered)是高速工作方式，
 * 			 只支持no-block socket。在这种模式下，当描述符从未就绪变为就绪时，内核通过epoll告诉你。
 * 			 然后它会假设你知道文件描述符已经就绪，并且不会再为那个文件描述符发送更多的就绪通知，直到你做了某些操作导致
 * 			 那个文件描述符不再为就绪状态了(比如，你在发送，接收或者接收请求，或者发送接收的数据少于一定量时导致了
 * 			 EAGAIN或者EWOULDBLOCK 错误，其实就是read或者write完成，如read返回-1）。但是请注意，
 * 			 如果一直不对这个fd作IO操作(从而导致它再次变成未就绪)，内核不会发送更多的通知(only once)
 * 			 ET模式在很大程度上减少了epoll事件被重复触发的次数，因此效率要比LT模式高。epoll工作在ET模式的时候，必须使用非阻塞套接口，
 * 			 以避免由于一个文件句柄的阻塞读/阻塞写操作把处理多个文件描述符的任务饿死）
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
