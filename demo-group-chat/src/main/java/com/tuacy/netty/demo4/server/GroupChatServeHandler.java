package com.tuacy.netty.demo4.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/13 23:52
 */
public class GroupChatServeHandler extends SimpleChannelInboundHandler<String> {

    // 定义一个Channel组，管理所有的Channel
    // GlobalEventExecutor.INSTANCE 是全局的事件执行器，是一个单利
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 连接建立，一旦连接建立，第一个被执行
     * 将当前Channel加入到ChannelGroup
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        // 将该客户加入聊天的信息推送给其他客户端
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 加入聊天\n");
        channelGroup.add(channel);
    }

    /**
     * 表示channel处于活动状态，提示 xx上线
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress() + " 上线了~");
    }

    /**
     * 表示channel处于非活动状态
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress() + " 离线了~");
    }

    /**
     * 断开连接
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        // 将该客户离开信息推送给其他客户端
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 离开聊天\n");
        // channelGroup里面的channel会自动移除掉，不需要手动调用
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        // 获取当前channel
        Channel channel = ctx.channel();
        // 这时我们遍历channelGroup,根据不同的情况，会送不同的消息
        channelGroup.forEach(ch-> {
            if (channel != ch) { // 不是当前的channel，转发消息
                ch.writeAndFlush("[客户]" + channel.remoteAddress() +  " 发送了消息:" + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 关闭
        ctx.close();
    }
}
