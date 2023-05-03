package com.example.boot09featuresprofile.config;


import com.example.boot09featuresprofile.bean.Color;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
public class MyConfig {

    @Bean
    @Profile("pro")
    public Color red(){
        return new Color();
    }

    @Bean
    @Profile("test")
    public Color green(){
        return new Color();
    }
}
