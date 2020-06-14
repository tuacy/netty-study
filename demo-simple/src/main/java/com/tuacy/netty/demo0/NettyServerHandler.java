package com.tuacy.netty.demo0;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 1. 我们自定义一个handler需要继承netty规定好的某个HandlerAdapter
 * 2. 这时我们自定的handler，才能称为handler
 * @author wuyx
 * @version 1.0
 * @date 2020/6/13 10:11
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据实现(这里我们可以读取客户端发送的消息)
     * @param ctx 上下文对象，含有管道pipeline，通道channel，连接地址
     * @param msg 客户端发送过来的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("server ctx = " + ctx);
        // 将msg转成一个ByteBuf(注意ByteBuf，ByteBuffer的区别，ByteBuf是netty提供)
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址:" + ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {

        // 将数据写入到缓存，并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端", CharsetUtil.UTF_8));
    }

    /**
     * 处理异常，一般直接关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
