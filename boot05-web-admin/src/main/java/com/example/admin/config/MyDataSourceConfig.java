package com.example.admin.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

@Deprecated
//@Configuration(proxyBeanMethods = false)
public class MyDataSourceConfig {

    //默认的自动配置是判断容器中没有才会配 @ConditionalOnMissingBean(DataSource.class)
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        //druidDataSource.setUrl();
        //druidDataSource.setUsername();
        //druidDataSource.setPassword();

        // 加入监控功能,防火墙功能
        druidDataSource.setFilters("stat,wall");
        //druidDataSource.setMaxActive(10);
        return druidDataSource;
    }

    /**
     * 配置druid监控页功能
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        StatViewServlet statViewServlet = new StatViewServlet();
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(statViewServlet, "/druid/*");
        //配置监控页面访问密码
        registrationBean.addInitParameter("loginUsername","mxy");
        registrationBean.addInitParameter("loginPassword","1234");
        return registrationBean;
    }

    /**
     * WebStatFilter用于采集web-jdbc关联监控的数据
     * @return
     */

    @Bean
    public FilterRegistrationBean webStatFilter(){
        WebStatFilter druidWebStatFilter = new WebStatFilter();
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(druidWebStatFilter,statViewServlet());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}
























