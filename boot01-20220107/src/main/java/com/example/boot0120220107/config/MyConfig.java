package com.example.boot0120220107.config;


import com.example.boot0120220107.bean.Car;
import com.example.boot0120220107.bean.Pet;
import com.example.boot0120220107.bean.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.*;
import org.springframework.web.filter.CharacterEncodingFilter;


/**
 * 1、配置类里面使用@Bean标注在方法上给容器注册组件，默认也是单实例的
 * 2、配置类本身也是组件
 * 3、proBeanMethods:代理bean的方法
 *      Full：全配置 proxyBeanMethods = true 保证每个@Bean方法被调用多少次返回的组件都是单实例的
 *      lite：轻量级配置 proxyBeanMethods = false 每个@Bean方法被调用多少次返回的都是新创建的
 *      解决问题：组件依赖
 *      组件依赖必须使用Full模式默认。其他默认都是Lite模式
 *
 *      @Import({User.class, DBNameResolver.class})
 *      给容器中自动配置这两个类型的组件，默认组件的名字为全类名
 *
 *      @ImportResource("classpath:beans.xml")导入Spring的配置文件，
 */

@Import({User.class})
@Configuration(proxyBeanMethods = true)  //配置类
@ImportResource("classpath:beans.xml")

@EnableConfigurationProperties(Car.class)
/**@EnableConfigurationProperties
 * 1、必须使用于配置类上
 * 2、开启Car的自动配置功能
 * 3、将Car这个组件自动注册到容器中
 */
public class MyConfig {

    @Bean
    /**
     * 向容器中添加组件。
     * 以方法名作为组件id。
     * 返回类型就是组件类型。
     * 返回的值，即为组件在容器中的实例
     */
    @ConditionalOnBean(name= "tom")
    //当容器中有tom组件时，标注方法（或类）生效
    public User user01(){
        User zhangsan = new User("zhangsan",18,tomcat());
        return zhangsan;
    }


    /**
     * 外部无论对配置类中的这个组件注册方法调用多少次，获取到的都是之前注册容器中的单实例对象
     * @return
     */

    //@Bean("tom")
    public Pet tomcat(){
        return new Pet("tom");
    }

    //@Bean
    //public CharacterEncodingFilter filter(){
    //    return null;
    //}

}





















































