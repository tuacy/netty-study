package com.tuacy.netty.demo.dubbo.customer;

import com.tuacy.netty.demo.dubbo.netty.NettyClient;
import com.tuacy.netty.demo.dubbo.publicinterface.HelloService;

/**
 * @author wuyx
 * @version 1.0
 * @date 2020/6/21 14:12
 */
public class ClientBootstrap {

    // 这里定义协议头
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) {
        // 创建一个消费者
        NettyClient customer = new NettyClient();

        // 创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);

        // 通过代理对象调用服务者的方法
        String res = service.hello("你好 dubbo~");
        System.out.println("调用的结果 res = " + res);
    }

}
