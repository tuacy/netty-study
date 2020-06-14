package com.tuacy.netty.demo.protobuf.pro;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 1. 我们自定义一个handler需要继承netty规定好的某个HandlerAdapter
 * 2. 这时我们自定的handler，才能称为handler
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/6/13 10:11
 */
//public class NettyServerHandler extends ChannelInboundHandlerAdapter {
//
//    /**
//     * 读取数据实现(这里我们可以读取客户端发送的消息)
//     * @param ctx 上下文对象，含有管道pipeline，通道channel，连接地址
//     * @param msg 客户端发送过来的数据
//     */
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        // 读取从客户端发送的StudentPOJO.Student
//        StudentPOJO.Student student = (StudentPOJO.Student) msg;
//        System.out.println("客户端发送的数据 id = " + student.getId() + " name = " + student.getName());
//    }
//
//    /**
//     * 数据读取完毕
//     */
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) {
//
//    }
//
//    /**
//     * 处理异常，一般直接关闭通道
//     */
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        ctx.close();
//    }
//}
public class NettyProServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) {
        if (msg.getDataType() == MyDataInfo.MyMessage.DataType.StudentType) {
            MyDataInfo.Student student = msg.getStudent();
            System.out.println("客户端发送的数据(Student) id = " + student.getId() + " name = " + student.getName());
        } else if (msg.getDataType() == MyDataInfo.MyMessage.DataType.WorkerType) {
            MyDataInfo.Worker worker = msg.getWorker();
            System.out.println("客户端发送的数据(Worker) name = " + worker.getName() + " age = " + worker.getAge());
        } else {
            System.out.println("传输的类型不对");
        }

    }

    /**
     * 处理异常，一般直接关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
