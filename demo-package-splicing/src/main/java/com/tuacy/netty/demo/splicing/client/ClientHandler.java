package com.tuacy.netty.demo.splicing.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/20 17:30
 */
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count = 0;

    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);

        String message = new String(buffer, CharsetUtil.UTF_8);
        System.out.println("客户端接收到消息 = " + message);
        System.out.println("客户端接收到消息数量 = " + (++count));

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 使用客户端发送10条数据，hello server
        for (int index = 0; index < 10; index++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello server " + index, StandardCharsets.UTF_8);
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
