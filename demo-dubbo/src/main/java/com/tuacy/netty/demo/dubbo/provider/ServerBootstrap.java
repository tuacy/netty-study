package com.tuacy.netty.demo.dubbo.provider;

import com.tuacy.netty.demo.dubbo.netty.NettyServer;

/**
 * ServerBootstrap 会启动一个服务提供者，就是NettyServer
 * @author wuyx
 * @version 1.0
 * @date 2020/6/21 13:51
 */
public class ServerBootstrap {

    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 8002);
    }

}
