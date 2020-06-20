package com.tuacy.netty.demo.protocol.client;

import com.tuacy.netty.demo.protocol.entity.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/20 17:30
 */
public class ClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        // 接收到数据，并处理

        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println("客户端接受到信息如下");
        System.out.println("长度 = " + len);
        System.out.println("内容 = " + new String(content, StandardCharsets.UTF_8));

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 使用客户端发送10条数据，hello server
        for (int index = 0; index < 10; index++) {
            String mes = "今天天气冷，吃火锅";
            byte[] content = mes.getBytes(StandardCharsets.UTF_8);
            int length = content.length;

            // 协议对象
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("异常信息：" + cause.getMessage());
        ctx.close();
    }
}
