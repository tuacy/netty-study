package com.tuacy.netty.demo.pool;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.SimpleChannelPool;

import java.net.InetSocketAddress;

/**
 * @version 1.0
 * @author: tuacy.
 * @date: 2020/6/22 21:09.
 */
public class SimpleChannelPoolMap extends AbstractChannelPoolMap<InetSocketAddress, SimpleChannelPool> {

    private Bootstrap bootstrap;
    private NettyClientHandler nettyClientHandler = new NettyClientHandler();

    public SimpleChannelPoolMap(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    @Override
    protected SimpleChannelPool newPool(InetSocketAddress key) {
        return new SimpleChannelPool(bootstrap.remoteAddress(key), new ChannelPoolHandler() {
            @Override
            public void channelReleased(Channel ch) throws Exception {
                System.out.println("channelReleased: " + ch);
            }

            @Override
            public void channelAcquired(Channel ch) throws Exception {
                System.out.println("channelAcquired: " + ch);
            }

            @Override
            public void channelCreated(Channel ch) throws Exception {
                // 为channel添加handler
                ch.pipeline().addLast(nettyClientHandler);
            }
        });
    }
}
