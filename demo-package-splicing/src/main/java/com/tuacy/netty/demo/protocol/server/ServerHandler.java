package com.tuacy.netty.demo.protocol.server;

import com.tuacy.netty.demo.protocol.entity.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/20 17:30
 */
public class ServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) {

        // 接收到数据，并处理

        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("服务端接受到信息如下");
        System.out.println("长度 = " + len);
        System.out.println("内容 = " + new String(content, StandardCharsets.UTF_8));

        // 回复消息
        String mes = UUID.randomUUID().toString();
        byte[] responseContent = mes.getBytes(StandardCharsets.UTF_8);
        int responseLength = responseContent.length;

        // 协议对象
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(responseLength);
        messageProtocol.setContent(responseContent);
        ctx.writeAndFlush(messageProtocol);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("异常：" + cause.getMessage());
        ctx.close();
    }
}
