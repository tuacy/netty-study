package com.tuacy.netty.demo.protobuf.pro;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Random;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/13 11:00
 */
public class NettyProClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道就绪就会触发改方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 随机的发送Student或者Worker对象

        int random = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage = null;
        if (0 == random) {
            // 发送Student
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StudentType).setStudent(MyDataInfo.Student.newBuilder().setId(8).setName("tuacy").build()).build();
        } else {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType).setWorker(MyDataInfo.Worker.newBuilder().setName("tuacy").setAge(30).build()).build();
        }
        ctx.writeAndFlush(myMessage);
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
