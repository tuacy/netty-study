package com.tuacy.netty.demo.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/13 11:00
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道就绪就会触发改方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 发送一个Student对象到服务器

        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("tuacy").build();
        ctx.writeAndFlush(student);
    }

    /**
     * 当通道有读取事件时会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    }

    /**
     * 处理异常，一般直接关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
