package com.tuacy.netty.demo1;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 1. 我们自定义一个handler需要继承netty规定好的某个HandlerAdapter
 * 2. 这时我们自定的handler，才能称为handler
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/6/13 10:11
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据实现(这里我们可以读取客户端发送的消息)
     *
     * @param ctx 上下文对象，含有管道pipeline，通道channel，连接地址
     * @param msg 客户端发送过来的数据
     */
    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        // 比如这里我们又一个比较耗时的操作业务 => 异步执行，提交该channel对应的NioEventLoop的taskQueue中。

        // 解决方案1 用户程序自定义的普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            public void run() {

                try {
                    Thread.sleep(10 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端（用户程序自定义的普通任务）", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 解决方案2 用户程序自定义定时任务 -> 该任务提交到 scheduleTaskQueue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            public void run() {

                try {
                    Thread.sleep(10 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端（用户程序自定义定时任务）", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 5, TimeUnit.SECONDS);


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
