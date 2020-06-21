package com.tuacy.netty.demo.dubbo.provider;

import com.tuacy.netty.demo.dubbo.publicinterface.HelloService;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/21 13:47
 */
public class HelloServiceImpl implements HelloService {
    /**
     * 当有消费方调用改方法时，就返回一个字符串
     */
    public String hello(String msg) {
        System.out.println("收到客户端消息 = " + msg);
        // 根据msg 返回不同的结果
        if (msg != null) {
            return "你好客户端，我已经收到你的消息 [" + msg + "]";
        } else {
            return "你好客户端，我已经收到你的消息 ";
        }
    }

}
