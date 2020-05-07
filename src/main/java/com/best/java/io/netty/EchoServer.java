package com.best.java.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * 1.创建ServerBootstrap实例来引导绑定和启动服务器
 * 2.创建NioEventLoopGroup对象来处理事件，如接受新连接、接收数据、写数据等等
 * 3.指定InetSocketAddress，服务器监听此端口
 * 4.设置childHandler执行所有的连接请求
 * 5.都设置完毕了，最后调用ServerBootstrap.bind() 方法来绑定服务器
 *
 *
 * 使用netty提供的非IO线程池处理业务耗时逻辑
 */
public class EchoServer {

	private int port;

	public EchoServer(int port) {
		this.port = port;
	}

	public void start() throws Exception {
		EventLoopGroup bossGroup = null;
		EventLoopGroup workGroup = null;
		// 使用netty自带的线程池处理耗时业务
		EventExecutorGroup businessGroup = new DefaultEventExecutorGroup(10);
		try {
			//因为使用NIO，指定NioEventLoopGroup来接受和处理新连接
			bossGroup = new NioEventLoopGroup();
			workGroup = new NioEventLoopGroup();
			//创建bootstrap来启动服务器
			ServerBootstrap boot = new ServerBootstrap();
			boot.group(bossGroup,workGroup)
					//指定通道类型为NioServerSocketChannel
					.channel(NioServerSocketChannel.class)
					.localAddress(port)
					// serverSocketChannel的配置;服务端地址可以复用，如某个进程占用了80端口,然后重启进程,原来的socket1处于TIME-WAIT状态,进程启动后,
					// 使用一个新的socket2,要占用80端口,如果这个时候不设置SO_REUSEADDR=true,那么启动的过程中会报端口已被占用的异常
					.option(ChannelOption.SO_REUSEADDR,true)
					// 使用内存池
					.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
					// socketChannel的配置
					//SO_KEEPALIVE=true,是利用TCP的SO_KEEPALIVE属性,当SO_KEEPALIVE=true的时候,服务端可以探测客户端的连接
					// 是否还存活着,如果客户端因为断电或者网络问题或者客户端挂掉了等,那么服务端的连接可以关闭掉,释放资源
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					//如果TCP_NODELAY没有设置为true,那么底层的TCP为了能减少交互次数,会将网络数据积累到一定的数量后,
					// 服务器端才发送出去,会造成一定的延迟。在互联网应用中,通常希望服务是低延迟的,建议将TCP_NODELAY设置为true
					.childOption(ChannelOption.TCP_NODELAY, true)
					//调用childHandler用来指定连接后调用的ChannelHandler
					.childHandler(new ChannelInitializer<Channel>() {
						//这个方法传ChannelInitializer类型的参数，ChannelInitializer是个抽象类，
						// 所以需要实现initChannel方法，这个方法就是用来设置ChannelHandler
						@Override
						protected void initChannel(Channel channel) throws Exception {
							// 将对象转为String，后续的EchoServerHandler就可以直接强转string而非ByteBuf
							channel.pipeline().addLast(new StringDecoder());
							channel.pipeline().addLast(businessGroup,new EchoServerHandler());
						}
					});
			ChannelFuture future = boot.bind().sync();
			System.out.println(EchoServer.class.getName() + " started and listen on " + future.channel().localAddress());
			future.channel().closeFuture().sync();
		} finally {
			if (bossGroup != null) {
				bossGroup.shutdownGracefully().sync();
			}
			if (workGroup != null) {
				workGroup.shutdownGracefully().sync();
			}
		}
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