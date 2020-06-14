package com.tuacy.netty.demo5.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/14 0:47
 */
public class HeartbeatServer {

    public static void main(String[] args) throws Exception {
        // 创建两个线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) // 在bossGroup增加一个日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 向pipeline加入一个netty提供的IdleStateHandler
                            /*
                             * IdleStateHandler是netty提供处理空闲状态的处理器
                             * 参数1 readerIdleTime：表示多长时间没有读，就会发送一个心跳检测包检测是否连接
                             * 参数2 writerIdleTime：表示多长时间没有写，就会发送一个心跳检测包检测是否连接
                             * 参数3 allIdleTime：表示多长时间即没有读也没有写，就会发送一个心跳检测包检测是否连接
                             * 参数4 unit: 单位
                             *
                             * 当 IdleStateHandler 触发后，就会传递给管道(pipeline)的下一个handler去处理，通过调用(触发)下一个handler的userEventTriggered
                             */
                            pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                            // 加入一个对空闲检测进一步处理的handler(自定义)
                            pipeline.addLast(new HeartbeatServerHandler());
                        }
                    });

            // 绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(7001).sync();

            // 监听关闭事件
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
