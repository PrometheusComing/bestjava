package com.best.java.io.bio;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *  优秀博文推荐地址：
 *  https://www.cnblogs.com/crazymakercircle/p/10225159.html
 *
 * 四大IO模型：BIO模型，NIO模型，IO多路复用，AIO。JDK NIO是结合了NIO + IO多路复用
 *
 *  JDK BIO（同步阻塞IO）：使用一个独立的Acceptor线程来监听客户端的连接（一个连接就是一个线程）
 *  弊端：每个客户端请求都需要一个线程去处理，无法满足高性能、高并发场景
 *
 *  JDK 伪异步IO：只是简单优化，底层仍旧使用bio，但是使用线程池去处理客户端请求，不会产生资源耗尽的情况
 *  弊端：如果客户端请求慢或者网络较慢时，服务端消息队列的其他消息将一直阻塞等待
 *
 *  该模型下的每个socket中，inputstream和outputstream的api说明中都是同步阻塞的，也就是当对socket的inputStream进行读取操作
 *  时它会一直阻塞下去，直到有数据可读或者数据读完或者空指针或者io异常，而对socket的outputStream进行写操作时也会一直阻塞，直到
 *  所有要发送的字节全部写入完毕或者发生异常。意味着如果客户端消息需要60s发送完毕，读取消息的线程就要阻塞60s，此时其他接入消息
 *  只能在消息队列中排队.(当前消息线程只能等着消息来，消息不来就只能等，干不了其他的事)
 *
 *  如果单线程服务器在等待数据时阻塞，那么第二个连接请求到来时，服务器是无法响应的。如果是多线程服务器，
 *  那么又会有为大量空闲（加粗，空闲资源浪费）请求产生新线程从而造成线程占用系统资源，线程浪费的情况。
 *  那么我们的问题就转移到，如何让单线程服务器在等待客户端数据到来时，依旧可以接收新的客户端连接请求,使用nio
 *
 *  JDK NIO（同步非阻塞IO）：使用IO多路复用(一个请求就是一个线程)，即客户端发送的连接请求都会注册到多路复用器上，多路复用器
 *  轮询到连接有I/O请求时才启动一个线程进行处理。用户进程也需要时不时的询问IO操作是否就绪，这就要求用户进程不停的去询问（同步）
 *  多路复用器可以响应其他客户端请求，是非阻塞的。进行io时，如果内核缓冲区无数据，系统调用立刻返回，也是非阻塞，但是当内核缓冲区
 *  有数据时，是阻塞的，直到数据从内核缓冲复制到用户进程缓冲。复制完成后，系统调用返回成功，应用进程开始处理用户空间的缓存数据。
 *
 *  JDK （Java AIO(NIO.2)）异步非阻塞IO:
 * 在此种模式下，用户进程只需要发起一个IO操作然后立即返回，等IO操作真正的完成以后，应用程序会得到IO操作完成的通知，此
 * 时用户进程只需要对数据进行处理就好了，不需要进行实际的IO读写操作，因为真正的IO读取或者写入操作已经由内核完成了。
 *
 * p.s.
 * 同步 ： 自己亲自出马持银行卡到银行取钱(使用同步IO时，Java自己处理IO读写)。
 *
 * 异步 ： 委托一小弟拿银行卡到银行取钱，然后给你(使用异步IO时，Java将IO读写委托给OS处理，需要将数据缓冲区地址和大小
 * 传给OS(银行卡和密码)，OS需要支持异步IO操作API)。
 *
 * 阻塞 ： ATM排队取款，你只能等待(使用阻塞IO时，Java调用会一直阻塞到读写完成才返回)。
 *
 * 非阻塞 ： 柜台取款，取个号，然后坐在椅子上做其它事，等号广播会通知你办理，没到号你就不能去，你可以不断问大堂经理排到了没有，
 * 大堂经理如果说还没到你就不能去(使用非阻塞IO时，如果不能读写Java调用会马上返回，当IO事件分发器会通知可读写时再继续进行读写，
 * 不断循环直到读写完成)。
 */
public class ClientSocket {
	public static void main(String[] args) {
		try {
			byte[] buffer = new byte[1024];

			// 建立连接
			Socket socket = new Socket("127.0.0.1",8080);
			String message = null;
			Scanner sc = new Scanner(System.in);
			message = sc.next();
			socket.getOutputStream().write(message.getBytes());
			System.out.println("等待服务端返回消息");
			socket.getInputStream().read(buffer);
			String content = new String(buffer);
			System.out.println("接收到服务器数据:" + content);
			socket.close();
			sc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
