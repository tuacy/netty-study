package com.tuacy.netty.demo.protocol.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/20 17:25
 */
public class Client {

    public static void main(String[] args) throws Exception{

       EventLoopGroup group = new NioEventLoopGroup();

       try {
           Bootstrap bootstrap = new Bootstrap()
                   .group(group)
                   .channel(NioSocketChannel.class)
                   .handler(new ClientInitializer());

           ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 7000).sync();
           channelFuture.channel().closeFuture().sync();
       } finally {
           group.shutdownGracefully();
       }

    }
}
