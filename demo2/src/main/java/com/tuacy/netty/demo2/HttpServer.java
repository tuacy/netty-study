package com.tuacy.netty.demo2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/13 16:38
 */
public class HttpServer {

    public static void main(String[] args) throws Exception {
        // 创建BossGroup和WorkerGroup,两个线程组
        // bossGroup，workerGroup含有的子线程(NioEventLoop)的个数，默认cpu核数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // 只是处理连接请求
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // 业务处理

        try {
            // 创建服务端启动对象，配置启动参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup) // 设置两个线程组
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerInitializer());

            ChannelFuture channelFuture = bootstrap.bind(8888).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
