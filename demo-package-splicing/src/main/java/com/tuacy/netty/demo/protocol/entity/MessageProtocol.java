package com.tuacy.netty.demo.protocol.entity;

/**
 * 协议包
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/6/20 18:02
 */
public class MessageProtocol {

    private int len;
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
