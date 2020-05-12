package com.best.java.io.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * @Author: xjxu3
 * @Date: 2020/5/8 12:07
 * @Description:  耗时业务处理
 */
public class TimeConsumeHandler extends ChannelInboundHandlerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(TimeConsumeHandler.class);


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		LOG.debug("{}，耗时任务开始执行...",Thread.currentThread().getName());
		try {
			// 模拟耗时，如查询数据库等等
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOG.debug("{}，耗时任务执行结束...",Thread.currentThread().getName());
		//可以直接写出去，也可以调用super.channelRead(ctx, msg);继续执行下一个handler
//		ChannelFuture cf = ctx.writeAndFlush(Unpooled.buffer().writeBytes("我收到了消息".getBytes(StandardCharsets.UTF_8)));
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
		super.channelRead(ctx, msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
