package com.example.boot0120220107;

import ch.qos.logback.classic.db.names.DBNameResolver;
import com.example.boot0120220107.bean.Pet;
import com.example.boot0120220107.bean.User;
import com.example.boot0120220107.config.MyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.jws.soap.SOAPBinding;

//@SpringBootApplication
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan("com.example.boot0120220107")
public class Boot0120220107Application {

    public static void main(String[] args) {

        //1、返回IOC容器
        ConfigurableApplicationContext run = SpringApplication.run(Boot0120220107Application.class, args);

        //2.查看容器中的组件
        String[] names = run.getBeanDefinitionNames();

        for (String name : names) {
            System.out.println(name);
        }


        ////3.从容器中获取组件
        //Pet tom1 = run.getBean("tom", Pet.class);
        //Pet tom02 = run.getBean("tom", Pet.class);
        //
        //if (tom1 == tom02){
        //    System.out.println("true");
        //}
        //
        ////4、com.example.boot0120220107.config.MyConfig$$EnhancerBySpringCGLIB$$2a0e7f84@6dcf13ed
        ////由SpringCGLIB增强的代理对象
        //MyConfig runBean = run.getBean(MyConfig.class);
        //System.out.println(runBean);
        //
        //
        ////5、如果 @Configuration(proxyBeanMethods = true)，springboot总会检查这个对象是否在容器总已经存在
        ////如果有，保持组件单实例
        //User user01 = runBean.user01();
        //User user02 = runBean.user01();
        //System.out.println(user01 == user02);
        //
        ////6.验证组建依赖：user01的宠物
        //Pet pet = user01.getPet();
        //Pet tom = run.getBean("tom",Pet.class);
        //System.out.println("张三的宠物：" + (pet == tom));
        //
        //
        //System.out.println("===============");
        //String[] name1 = run.getBeanNamesForType(User.class);
        //for (String s : name1) {
        //    System.out.println(s);
        //}

        boolean user01 = run.containsBean("user01");
        System.out.println(user01);


        boolean user = run.containsBean("user");
        System.out.println(user);

        boolean cat = run.containsBean("cat");
        System.out.println(cat);

    }

}
