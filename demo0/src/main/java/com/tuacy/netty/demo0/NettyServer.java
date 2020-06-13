package com.tuacy.netty.demo0;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/13 8:57
 */
public class NettyServer {

    public static void main(String[] args) throws Exception {
        // 创建BossGroup和WorkerGroup,两个线程组
        // bossGroup，workerGroup含有的子线程(NioEventLoop)的个数，默认cpu核数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // 只是处理连接请求
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // 业务处理

        try {
            // 创建服务端启动对象，配置启动参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup) // 设置两个线程组
                    .channel(NioServerSocketChannel.class) // 使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列等到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个通道测试对象(匿名对象)
                        /**
                         * 给pipeline设置处理器
                         */
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    }); // 给我们得workerGroup的EventLoop对应的管道设置处理器

            // 绑定一个端口并且同步。生成了一个ChannelFuture对象
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {

            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}
