package com.tuacy.netty.demo.handle.client;

import com.tuacy.netty.demo.handle.server.ByteToLongDecoder;
import com.tuacy.netty.demo.handle.server.ByteToLongDecoder2;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/20 14:52
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel ch) {

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new ByteToLongDecoder2());

        // 加入一个出站的handler，对数据进行编码
        pipeline.addLast(new LongToByteEncoder());

        // 加入一个自定义的handler,处理业务逻辑
        pipeline.addLast(new ClientHandler());

    }

}
