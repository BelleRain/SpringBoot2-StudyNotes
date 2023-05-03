package com.example.admin.actuator.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
public class AppInfoInfoContributor implements InfoContributor {

    //应用于一些信息需要通过代码运行才可得到的场景
    @Override
    public void contribute(Info.Builder builder) {

        builder.withDetail("msg","你好")
                .withDetail("hello","atguigu")
                .withDetails(Collections.singletonMap("world","666600"));
    }
}
