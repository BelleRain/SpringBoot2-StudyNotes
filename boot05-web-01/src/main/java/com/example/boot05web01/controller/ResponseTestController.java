package com.example.boot05web01.controller;

import com.example.boot05web01.bean.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Controller
public class ResponseTestController {
    /**
     * 1、浏览器发请求直接返回xml     [application/xml]    jacksonXmlConverter
     * 2、如果为Ajax请求，返回json   [application/json]   jacksonJsonConverter
     * 3、如果硅谷app发请求，返回自定义协议数据   [application/x-guigu]  xxxxConverter
     *          属性值1;属性值2
     *
     * 步骤：
     * 1、添加自定义的MessageConverter进系统底层
     * 2、系统底层就会统计出所有MessageConverter能操作哪些类型
     * 3、客户端内容协商 [guigu--->guigu]
     * @return
     */
    @ResponseBody  //利用返回值处理器中的消息转换器进行处理
    @GetMapping("/test/person")
    public Person getPerson(){
        Person person = new Person();
        person.setAge(18);
        person.setBirth(new Date());
        person.setUserName("zhangsan");
        return person;
    }


}
