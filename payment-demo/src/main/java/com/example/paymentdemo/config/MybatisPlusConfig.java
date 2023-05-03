package com.example.paymentdemo.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@MapperScan("com.example.paymentdemo.mapper")
@EnableTransactionManagement //开启事务管理
public class MybatisPlusConfig {
}
