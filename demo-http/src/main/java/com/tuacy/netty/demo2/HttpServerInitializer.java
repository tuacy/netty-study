package com.tuacy.netty.demo2;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/13 16:40
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel ch) {
        // 向管道加入处理器

        // 得到管道
        ChannelPipeline pipeline = ch.pipeline();

        // 1. 加入一个Netty提供的HttpServerCoder codec = [coder + decoder]
        // HttpServerCodec： netty提供的处理http编解码器
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());
        // 2. 增加一个自定义的handler
        pipeline.addLast("HttpServerHandler", new HttpServerHandler());
    }
}
