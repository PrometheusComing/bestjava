package com.best.java.io.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: xjxu3
 * @Date: 2020/4/28 17:15
 * @Description:
 */
public class EchoClient {

	private final int port;

	private final String host;

	public EchoClient(String host, int port) {
		this.port = port;
		this.host = host;
	}

	public void start() throws Exception {
		EventLoopGroup group = null;
		try {
			// 初始化nio线程池，在里面创建EventExecutor[] children,并设置线程选择器EventExecutorChooser(根据children长度，是2的幂
			// 就用按位选择器，否则使用轮询选择器。用于选出NioEventLoop与channel绑定),不指定executor时，executor为
			// ThreadPerTaskExecutor(线程执行器，里面封装了线程工厂)，再通过for循环为children数组创建元素(元素就是NioEventLoop实例，
			// NioEventLoop间接实现了EventExecutor)并将线程执行器绑定到NioEventLoop
			// 见MultithreadEventExecutorGroup 84行
			// 此时每个NioEventLoop的selector也已经创建，但是当前NioEventLoop的thread还是null，也没有启动。每个NioEventLoop的parent
			// 都是同一个NioEventLoopGroup实例。所以NioEventLoop其实就可以看成是一个线程的封装
			// 可以debug后观察下
			group = new NioEventLoopGroup();
			Bootstrap bootstrap = new Bootstrap();
			// 将group设置到bootstrap
			bootstrap.group(group)
					// 初始化了一个用于生产指定channel类型的工厂实例
					.channel(NioSocketChannel.class)
					.option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
					.option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
					// 表示客户端调用服务端接口的超时时间。这个设置可以当做一个默认设置,我们在应用层,
					// 服务端接口一定要能支持设置超时时间,因为不同的业务服务接口,针对不同场景,超时时间可能是不同的
					.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
					.remoteAddress(host, port)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							socketChannel.pipeline().addLast(new EchoClientHandler());
						}
					});

			ChannelFuture channelFuture = bootstrap.connect().sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			if (group != null) {
				group.shutdownGracefully().sync();
			}
		}
	}

	public static void main(String[] args) {
		EchoClient echoClient = new EchoClient("192.168.0.105", 20000);
		try {
			echoClient.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
