package com.tuacy.netty.demo.handle.server;

import com.tuacy.netty.demo.handle.client.LongToByteEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/20 13:38
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel ch) {

        ChannelPipeline pipeline = ch.pipeline();

        // 入站的handler进行解码，
        pipeline.addLast(new ByteToLongDecoder2());
        // 编码
        pipeline.addLast(new LongToByteEncoder());
        // 自定义的handler处理业务逻辑
        pipeline.addLast(new ServerHandler());

    }
}
