package com.tuacy.netty.demo6.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;


/**
 * 这里 TextWebSocketFrame 类型，表示一个文本帧(frame)
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/6/14 9:36
 */
public class WebSocketServerFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        System.out.println("服务器端收到消息 " + msg.text());

        // 回复消息
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间" + LocalDateTime.now() + " " + msg.text()));
    }

    /**
     * 当web客户端链接之后，就会触发
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        // id 表示唯一的值
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asLongText());
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asShortText());
    }

    /**
     * 当web客户端断开连接之后，就会触发
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("handlerRemoved 被调用" + ctx.channel().id().asLongText());
        System.out.println("handlerRemoved 被调用" + ctx.channel().id().asShortText());
    }

    /**
     * 处理异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生 " + cause.getMessage());
        ctx.close();
    }
}
