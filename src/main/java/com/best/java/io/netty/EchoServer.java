package com.best.java.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 1.创建ServerBootstrap实例来引导绑定和启动服务器
 * 2.创建NioEventLoopGroup对象来处理事件，如接受新连接、接收数据、写数据等等
 * 3.指定InetSocketAddress，服务器监听此端口
 * 4.设置childHandler执行所有的连接请求
 * 5.都设置完毕了，最后调用ServerBootstrap.bind() 方法来绑定服务器
 */
public class EchoServer {

	private int port;

	public EchoServer(int port) {
		this.port = port;
	}

	public void start() throws Exception {
		//因为使用NIO，指定NioEventLoopGroup来接受和处理新连接
		EventLoopGroup group = new NioEventLoopGroup();
		//创建bootstrap来启动服务器
		ServerBootstrap boot = new ServerBootstrap();
		boot.group(group).
				//指定通道类型为NioServerSocketChannel
						channel(NioServerSocketChannel.class).
				localAddress(port).
				//调用childHandler用来指定连接后调用的ChannelHandler
						childHandler(new ChannelInitializer<Channel>() {
					//这个方法传ChannelInitializer类型的参数，ChannelInitializer是个抽象类，所以需要实现initChannel方法，这个方法就是用来设置ChannelHandler
					@Override
					protected void initChannel(Channel channel) throws Exception {
						channel.pipeline().addLast(new EchoServerHandler());
					}
				});
		ChannelFuture future = boot.bind().sync();
		System.out.println(EchoServer.class.getName() + " started and listen on " + future.channel().localAddress());
		future.channel().closeFuture().sync();


	}


	public static void main(String[] args) {
		EchoServer server = new EchoServer(20000);
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}