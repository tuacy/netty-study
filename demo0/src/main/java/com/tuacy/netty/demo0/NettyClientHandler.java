package com.tuacy.netty.demo0;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/13 11:00
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道就绪就会触发改方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("client " + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, server", CharsetUtil.UTF_8));
    }

    /**
     * 当通道有读取事件时会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 将msg转成一个ByteBuf(注意ByteBuf，ByteBuffer的区别，ByteBuf是netty提供)
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器回复的消息：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器端地址:" + ctx.channel().remoteAddress());
    }

    /**
     * 处理异常，一般直接关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
