package com.tuacy.netty.demo.pool;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

import java.net.InetSocketAddress;

/**
 * @version 1.0
 * @author: tuacy.
 * @date: 2020/6/22 21:07.
 */
public class NettyClient {

    private static SimpleChannelPoolMap poolMap;

    public static void main(String[] args) {

        NioEventLoopGroup group = new NioEventLoopGroup(1, new DefaultThreadFactory("Client-Event", false));
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        poolMap = new SimpleChannelPoolMap(bootstrap);
        SimpleChannelPool channelPool = poolMap.get(new InetSocketAddress(6668));
        // 从连接池获取一个连接
        channelPool.acquire().addListener(new FutureListener<Channel>() {
            @Override
            public void operationComplete(Future<Channel> future) throws Exception {
                if (future.isSuccess()) {
                    Channel channel = future.getNow();
                    channel.writeAndFlush(Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8));
                    // 将连接放入连接池
                    channelPool.release(channel);
                }
                if (future.cause() != null) {
                    System.out.println(future.cause());
                }
            }
        });
    }

}
