package com.demo.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {


    @RequestMapping("/")
    public String Hello() {
        return "Hello world!";
    }
}
