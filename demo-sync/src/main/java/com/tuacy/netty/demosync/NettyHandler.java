package com.tuacy.netty.demosync;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @version 1.0
 * @author: tuacy.
 * @date: 2020/6/22 19:16.
 */
public class NettyHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //消息唯一标识这里演示写为msg-sn,正常流程应由服务端返回,这里解析
        String key = "msg-sn";
        String msgStr = msg.toString();

        try {
            //消息唯一标识这里演示写为test-key,正常流程应由服务端返回,这里解析
            NettyTools.setReceiveMsg(key, msgStr);
        } catch (Exception e) {
            System.out.println("消息转码失败:{} ");
            e.printStackTrace();
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println("异常:{}");
        ctx.fireExceptionCaught(cause);
    }
}
