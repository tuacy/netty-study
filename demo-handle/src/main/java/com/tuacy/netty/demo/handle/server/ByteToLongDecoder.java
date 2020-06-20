package com.tuacy.netty.demo.handle.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/20 13:40
 */
public class ByteToLongDecoder extends ByteToMessageDecoder {

    /**
     * @param ctx 上下文
     * @param in  入站的ByteBuf
     * @param out List集合，将加码后的数据传给下一个handler
     * @throws Exception 异常
     */
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 因为long是8个字节，需要判断有8个字节才能读取一个long
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
