package com.tuacy.netty.demo2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * 1. SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter子类
 * 2. HttpObject 客户端和服务端相互通讯的数据被封装成HttpObject
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/6/13 16:38
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端数据
     */
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // 判断 msg 是不是一个HttpRequest请求
        if (msg instanceof HttpRequest) {
            System.out.println("msg 类型 = " + msg.getClass());
            System.out.println("客户端地址：" + ctx.channel().remoteAddress());

            // 获取到
            HttpRequest httpRequest = (HttpRequest) msg;
            // 获取uri,过滤指定的资源
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了favicon.ico，不做相应");
                return;
            }

            // 回复信息给浏览器 [http协议]
            ByteBuf content = Unpooled.copiedBuffer("hello, 我是服务器", CharsetUtil.UTF_8);
            // 构造一个http的相应，既HttpResponse
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain"); // 类型
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes()); // 长度

            // 将构建好的response返回
            ctx.writeAndFlush(httpResponse);
        }
    }
}
