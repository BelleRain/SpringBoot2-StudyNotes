package com.example.boot09featuresprofile.controller;


import com.example.boot09featuresprofile.bean.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {

    @Value("${person.name:李四}")
    private String name;

    @Value("${MAVEN_HOME}")
    private String msg;

    @Value("${os.name}")
    private String osName;

    @Autowired
    private Person person;

    @GetMapping("/")
    public String hello(){
        return "Hello" + name;
    }

    //@GetMapping("/msg")
    //public String getMsg(@Value("${MAVEN_HOME}") String msg,
    //                     @Value("${os.name}") String osName){
    //    return msg + "---->" + osName;
    //}

    @GetMapping("/msg")
    public String getMsg(){
        return msg + "---->" + osName;
    }

    @GetMapping("/person")
    public String getPerson(){
        return person.getClass().toString();
    }

    @GetMapping("/personinfo")
    public Person getPersonInfo(){
        return person;
    }

}
