package com.tuacy.netty.demo3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/13 22:26
 */
public class NettyByteBuf01 {

    public static void main(String[] args) {
        // 创建一个ByteBuf
        // 1. 创建一个对象，改对象包含一个数组arr，是一个byte[10]
        // 2. 在netty的buffer中，不需要flip记性反转(底层维护了readerIndex和writerIndex)
        ByteBuf byteBuf = Unpooled.buffer(10);

        //
        for (int i = 0; i < 10; i++) {
            byteBuf.writeByte(i);
        }

        // 输出
//        for (int i = 0; i < byteBuf.capacity(); i++) {
//            System.out.println(byteBuf.getByte(i));
//        }
        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.readByte());
        }
    }

}
