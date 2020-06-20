package com.tuacy.netty.demo.splicing.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/20 17:30
 */
public class ServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count = 0;

    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {

        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);

        String message = new String(buffer, StandardCharsets.UTF_8);
        System.out.println("服务端接受到数据 " + message);
        System.out.println("服务端接受到的消息良 = " + (++count));

        // 服务器会送数据给客户端，会送一个随机id
        ByteBuf responseByteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), CharsetUtil.UTF_8);
        ctx.writeAndFlush(responseByteBuf);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
