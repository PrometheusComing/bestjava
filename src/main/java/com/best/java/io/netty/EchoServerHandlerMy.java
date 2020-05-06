package com.best.java.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: xjxu3
 * @Date: 2020/5/5 21:56
 * @Description:
 */
public class EchoServerHandlerMy  extends ChannelInboundHandlerAdapter {
	private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();


	//重写该方法，该方法会被调用用来接收数据
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("server channelRead...");
		System.out.println("Server reveived: " + ((ByteBuf) msg).toString(StandardCharsets.UTF_8));
//		Channel channel = ctx.channel();
//		ChannelFuture cf = channel.writeAndFlush(Unpooled.buffer().writeBytes("我收到了".getBytes(StandardCharsets.UTF_8)));

		//创建将数据写到Channel的Runnable
		Runnable writer = new Runnable() {
			@Override
			public void run() {
				ChannelFuture cf = ctx.writeAndFlush(Unpooled.buffer().writeBytes("我收到了".getBytes(StandardCharsets.UTF_8)));
				cf.addListener(new ChannelFutureListener() {
					@Override
					public void operationComplete(ChannelFuture future) throws Exception {
						//写操作完成，并没有错误发生
						if (future.isSuccess()){
							System.out.println("successful");
						}else{
							//记录错误
							System.out.println("error");
							future.cause().printStackTrace();
						}
					}
				});
			}
		};
		EXECUTOR.execute(writer);

	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
