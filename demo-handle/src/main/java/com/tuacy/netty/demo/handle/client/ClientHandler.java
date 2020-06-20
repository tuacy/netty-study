package com.tuacy.netty.demo.handle.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/20 14:56
 */
public class ClientHandler extends SimpleChannelInboundHandler<Long> {

    protected void channelRead0(ChannelHandlerContext ctx, Long msg) {
        System.out.println("从服务端" + ctx.channel().remoteAddress() + " 读取到long " + msg.toString());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("发送数据");
        ctx.writeAndFlush(123456L); // 发送一个long
    }
}
