package com.example.boot0120220107.controller;


import com.example.boot0120220107.bean.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @Autowired
    Car car;

    @RequestMapping("/car")
    public Car car(){
        return car;
    }



}
