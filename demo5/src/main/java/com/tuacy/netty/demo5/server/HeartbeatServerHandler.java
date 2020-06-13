package com.tuacy.netty.demo5.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/14 1:04
 */
public class HeartbeatServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception 异常
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            String eventType = null;
            switch (idleStateEvent.state()) {
                case READER_IDLE: // 读空闲
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE: // 写空闲
                    eventType = "写空闲";
                    break;
                case ALL_IDLE: // 读写空闲
                    eventType = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "--超时事件--" + eventType);
        }
    }
}
