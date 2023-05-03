package com.example.boot02helloworld.controller;

import com.example.boot02helloworld.boot.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Autowired
    private Person person;

    @RequestMapping("/hello")
    public Person hello(){
        String userName = person.getUserName();
        System.out.println(userName);
        return person;
    }
}
