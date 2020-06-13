package com.tuacy.netty.demo4.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/13 23:53
 */
public class GroupChatClient {

    private final String host;
    private final int port;

    public GroupChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 向pipeline加入一个解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            // 向pipeline加入一个编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            // 加入自己的业务处理handler
                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    });

            // 连接
            ChannelFuture channelFuture = bootstrap.connect(this.host, this.port).sync();
            Channel channel = channelFuture.channel();
            System.out.println("-------------" + channel.localAddress() + "----------------");
            // 客户端需要输入信息,创建一个扫描器
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                // 通过channel，发送到服务端
                channel.writeAndFlush(msg + "\r\n");
            }
            // 监听关闭
            channelFuture.channel().closeFuture().sync();

        } finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        new GroupChatClient("127.0.0.1", 7001).run();
    }

}
