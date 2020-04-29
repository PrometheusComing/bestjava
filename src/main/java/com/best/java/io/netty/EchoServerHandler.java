package com.best.java.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.Charset;

/**
 *   Netty中有两个方向的数据流，上图显示的入站(ChannelInboundHandler)和出站(ChannelOutboundHandler)之间有一个明显的区别：
 *   若数据是从用户应用程序到远程主机则是“出站(outbound)”，
 *   相反若数据时从远程主机到用户应用程序则是“入站(inbound)”。
 *   为了使数据从一端到达另一端，一个或多个ChannelHandler将以某种方式操作数据。
 *   这些ChannelHandler会在程序的“引导”阶段被添加ChannelPipeline中，并且被添加的顺序将决定处理数据的顺序。
 *   ChannelPipeline的作用我们可以理解为用来管理ChannelHandler的一个容器，每个ChannelHandler处理各自的数据(例如入站数据只能由ChannelInboundHandler处理)，
 *   处理完成后将转换的数据放到ChannelPipeline中交给下一个ChannelHandler继续处理，直到最后一个ChannelHandler处理完成。
 *
 *
 *   Netty使用多个Channel Handler来对事件处理的分离，因为可以很容的添加、更新、删除业务逻辑处理handler
 *   它的每个方法都可以被重写，它的所有的方法中只有channelRead方法是必须要重写的。
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {


	//重写该方法，该方法会被调用用来接收数据
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Server reveived: " + ((ByteBuf) msg).toString(Charset.forName("UTF-8")));
		Channel channel = ctx.channel();

		channel.writeAndFlush(Unpooled.buffer().writeBytes("我收到了".getBytes("UTF-8")));
		ReferenceCountUtil.release(msg);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {


	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
