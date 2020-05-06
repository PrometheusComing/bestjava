package com.best.java.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * @Author: xjxu3
 * @Date: 2020/4/30 10:06
 * @Description:
 */
public class EchoClientHandler extends SimpleChannelInboundHandler {

	private static final Logger LOG = LoggerFactory.getLogger(EchoClientHandler.class);


	@Override
	// 连接后调用
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		LOG.info("{}:client channelActive...",Thread.currentThread().getName());
		ctx.writeAndFlush(Unpooled.buffer().writeBytes("fuck u doubi".getBytes(StandardCharsets.UTF_8)));
	}

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
		LOG.info("{}:client channelRead0...",Thread.currentThread().getName());
		LOG.info("{}:client get msg :{}",Thread.currentThread().getName(),((ByteBuf)o).toString(StandardCharsets.UTF_8));

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
