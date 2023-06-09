package com.atguigu.atguiguhellospringbootstarterautoconfigure.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("atguigu.hello")
public class HelloProperties {
    private String prefix;
    private String suffix;

    public HelloProperties(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public HelloProperties() {
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
