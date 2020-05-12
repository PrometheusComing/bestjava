package com.best.java.io.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 *
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(EchoServerHandler.class);

	//重写该方法，该方法会被调用用来接收数据
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		LOG.info("{}:server channelRead...",Thread.currentThread().getName());
		LOG.info("{}:server reveived:{}",Thread.currentThread().getName(),msg);
//		ChannelFuture cf = ctx.writeAndFlush(Unpooled.buffer().writeBytes("我收到了".getBytes(StandardCharsets.UTF_8)));
//		cf.addListener(new ChannelFutureListener() {
//			@Override
//			public void operationComplete(ChannelFuture future) throws Exception {
//				//写操作完成，并没有错误发生
//				if (future.isSuccess()){
//					LOG.info("{}:server process sucess",Thread.currentThread().getName());
//				}else{
//					//记录错误
//					LOG.info("{}:server process fail",Thread.currentThread().getName());
//					future.cause().printStackTrace();
//				}
//			}
//		});
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
