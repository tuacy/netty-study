package com.tuacy.netty.demo.handle.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/20 14:54
 */
public class LongToByteEncoder extends MessageToByteEncoder<Long> {

    /**
     * 编码
     */
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) {
        out.writeLong(msg);
    }
}
