package com.tuacy.netty.demo.dubbo.publicinterface;

/**
 * 这个是接口，是服务提供方和服务消费方都需要
 *
 * @author wuyx
 * @version 1.0
 * @date 2020/6/21 13:45
 */
public interface HelloService {

    String hello(String msg);

}
