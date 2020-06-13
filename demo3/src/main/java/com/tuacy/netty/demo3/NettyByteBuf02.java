package com.tuacy.netty.demo3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/13 22:26
 */
public class NettyByteBuf02 {

    public static void main(String[] args) {
        // 创建一个ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", CharsetUtil.UTF_8);

        // 使用相关的方法
        if(byteBuf.hasArray()) {
           byte[] content = byteBuf.array();
           // 将content转成字符串
            System.out.println(new String(content, CharsetUtil.UTF_8));
        }
    }

}
