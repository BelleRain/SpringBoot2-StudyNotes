package com.example.boot09featuresprofile.bean;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile(value = "pro")
@Data
@Component
@ConfigurationProperties("person")
public class Boss implements Person{

    private String name;
    private int age;

}
