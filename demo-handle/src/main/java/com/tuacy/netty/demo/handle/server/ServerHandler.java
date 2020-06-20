package com.tuacy.netty.demo.handle.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/20 14:48
 */
public class ServerHandler extends SimpleChannelInboundHandler<Long> {

    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

        System.out.println("从客户端" + ctx.channel().remoteAddress() + " 读取到long " + msg.toString());

        // 给客户端发哦送一个long
        ctx.writeAndFlush(98765L);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
