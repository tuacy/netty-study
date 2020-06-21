package com.tuacy.netty.demo.dubbo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/21 13:58
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable<String> {

    private ChannelHandlerContext context; // 上下文
    private String result; // 返回结果
    private String param; // 客户端调用方法时，传入的参数

    /**
     * 与服务器的连接创建之后就会调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    /**
     * 收到服务器的数据之后调用
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify(); // 唤醒等待的线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 被代理对象调用，发送数据给服务器 -> wait 等待被唤醒 -> 返回结果
     */
    public synchronized String call() throws Exception {
        context.writeAndFlush(param);
        // 进行wait
        wait();
        return result; // 服务方返回的结果
    }

    public void setParam(String param) {
        this.param = param;
    }
}
