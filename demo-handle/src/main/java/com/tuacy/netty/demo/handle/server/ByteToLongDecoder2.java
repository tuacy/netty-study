package com.tuacy.netty.demo.handle.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/20 17:10
 */
public class ByteToLongDecoder2 extends ReplayingDecoder<Void> {


    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // ReplayingDecoder 不需要判断数据是否足够读取，内部会进行处理
        out.add(in.readLong());

    }
}
