package com.atguigu.atguiguhellospringbootstarterautoconfigure.service;

import com.atguigu.atguiguhellospringbootstarterautoconfigure.beans.HelloProperties;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 默认不要放在容器中
 */
public class HelloService {

    @Autowired
    HelloProperties helloProperties;

    public String sayHello(String username ){
        return helloProperties.getPrefix() + ":" + username + "--->" + helloProperties.getSuffix();
    }
}
