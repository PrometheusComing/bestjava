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

	private int port;

	private String host;

	public EchoClient(String host, int port) {
		this.port = port;
		this.host = host;
	}

	public void start() throws Exception {
		EventLoopGroup group = null;
		try {
			group = new NioEventLoopGroup();
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
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
