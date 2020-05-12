package com.best.java.io.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author: xjxu3
 * @Date: 2020/5/12 22:25
 * @Description: 权限验证，验证通过后，这个handler就无用可以移除了
 */
public class AuthHandler extends SimpleChannelInboundHandler<Object> {
	private static final Logger LOG = LoggerFactory.getLogger(AuthHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		if ("fuck u doubi".equals(msg)) {
			LOG.info("验证通过");
			ctx.pipeline().remove(this);
			ctx.fireChannelRead(msg);
		} else {
			LOG.info("验证未通过");
			ctx.close();
		}
	}
}
