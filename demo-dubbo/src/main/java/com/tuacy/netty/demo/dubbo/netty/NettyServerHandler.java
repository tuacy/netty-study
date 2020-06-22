package com.tuacy.netty.demo.dubbo.netty;

import com.tuacy.netty.demo.dubbo.customer.ClientBootstrap;
import com.tuacy.netty.demo.dubbo.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/21 13:56
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 获取客户端发送的消息，并调用服务
        System.out.println("msg = " + msg);
        // 客户端在调用服务的api时，我们需要定义一个协议
        // 比如我们要求，每次发消息使都必须以某个字符串开头“HelloService#hello#”
        if (msg.toString().startsWith(ClientBootstrap.providerName)) {
            String result = new HelloServiceImpl().hello(msg.toString().substring(ClientBootstrap.providerName.length()));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
