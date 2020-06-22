package com.tuacy.netty.demosync;

import io.netty.channel.Channel;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

/**
 * @version 1.0
 * @author: tuacy.
 * @date: 2020/6/22 19:22.
 */
public class NettyClient {

    private static FixedChannelPool getPool(String serverAddr) {
        if (NettyPool.poolMap == null || !NettyPool.poolMap.contains(serverAddr)) {
            //为null时初始化一次再获取
            new NettyPool();
        }
        return NettyPool.poolMap.get(serverAddr);
    }

    public static void main(String[] args) {

        String serverAddr = "127.0.0.1:6668";
        String key = "msg-sn";

        final FixedChannelPool pool = getPool(serverAddr);

        //申请连接，没有申请到或者网络断开，返回null
        Future<Channel> future = pool.acquire();
        NettyTools.initReceiveMsg(key);

        future.addListener(new FutureListener<Channel>() {
            @Override
            public void operationComplete(Future<Channel> future) throws Exception {
                //给服务端发送数据
                Channel channel = future.getNow();

                //将消息发送到服务端
                channel.writeAndFlush("测试");

                // 连接放回连接池，这里一定记得放回去
                pool.release(channel);
            }
        });


        //等待并获取服务端响应
        String result = NettyTools.waitReceiveMsg(key);
        System.out.println(result);
    }

}
