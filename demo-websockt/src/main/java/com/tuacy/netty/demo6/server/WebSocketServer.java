package com.tuacy.netty.demo6.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/14 9:23
 */
public class WebSocketServer {

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
                            // 因为是基于http协议，所以我们需要使用http编解码器
                            pipeline.addLast(new HttpServerCodec());
                            // 是以块方式写，添加ChunkedWriteHandler处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            /*
                              说明：
                              1. http数据在传输过程中是分段的，HttpObjectAggregator可以将多个段聚合起来。
                              2. 这就是为什么，当浏览器发送大量数据时，就会发出多次http请求。
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /*
                            说明：
                            1. 对于websocket，他的数据是以帧(frame)形式传递。
                            2. 可以看到WebSocketFrame下面有六个子类。
                            3. 浏览器请求时 ws:localhost:7000/xxx 表示请求的uri
                            4. WebSocketServerProtocolHandler 将一个http协议升级为ws协议
                            5. 是通过一个状态码 101
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            // 自定义handler,处理业务逻辑
                            pipeline.addLast(new WebSocketServerFrameHandler());
                        }
                    });

            // 启动服务器绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(7001).sync();

            // 监听关闭事件
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
