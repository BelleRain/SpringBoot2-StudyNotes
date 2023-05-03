package com.atguigu.atguiguhellospringbootstarterautoconfigure.auto;


import com.atguigu.atguiguhellospringbootstarterautoconfigure.beans.HelloProperties;
        import com.atguigu.atguiguhellospringbootstarterautoconfigure.service.HelloService;
        import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
        import org.springframework.boot.context.properties.EnableConfigurationProperties;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HelloProperties.class)
public class HelloServiceAutoConfiguration {

    @ConditionalOnMissingBean(HelloService.class)
    @Bean
    public HelloService helloService(){
        HelloService helloService = new HelloService();
        return helloService;
    }
}
