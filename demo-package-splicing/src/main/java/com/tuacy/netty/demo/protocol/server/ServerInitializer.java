package com.tuacy.netty.demo.protocol.server;

import com.tuacy.netty.demo.protocol.coder.MessageProtocolDecoder;
import com.tuacy.netty.demo.protocol.coder.MessageProtocolEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/20 17:32
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new MessageProtocolEncoder());
        pipeline.addLast(new MessageProtocolDecoder());
        pipeline.addLast(new ServerHandler());
    }
}
