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
		ChannelFuture cf = ctx.writeAndFlush(Unpooled.buffer().writeBytes("我收到了".getBytes(StandardCharsets.UTF_8)));
		cf.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				//写操作完成，并没有错误发生
				if (future.isSuccess()){
					LOG.info("{}:server process sucess",Thread.currentThread().getName());
				}else{
					//记录错误
					LOG.info("{}:server process fail",Thread.currentThread().getName());
					future.cause().printStackTrace();
				}
			}
		});
	}
	@Override
	/*
	  channelReadComplete方法的回调时机不受影响，即它的回调时机和是否使用了解码器没有一毛钱关系，
	  它只认可JDK的Socketchannel是否读完了，两个判断条件：
	  下次读到0个字节
	  本次读到的字节数小于当前缓冲区的容量
	  满足一个，就会触发一次调用，和业务上的一个完整数据包的设置没有任何关系
	  所以要慎用
	 */
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
//	异常如果不处理的话，传播的顺序和用户添加的顺序一致，即都是正向的，即不论异常抛出是发生在inbound事件还是outbound事件的handler中，
//	其调用的顺序与它们被用户添加的顺序一致。比如出站handler里的异常捕获事件，仍然是正向传播的。可以看父类的exceptionCaught方法
//  里面调用了ctx.fireExceptionCaught(cause);来传递。这里获取异常直接就关闭了ctx
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
