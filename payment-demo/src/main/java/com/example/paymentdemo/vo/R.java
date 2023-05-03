package com.example.paymentdemo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)  //使所有的set方法返回类型为当前类，实现链式调用
public class R {


    private Integer code;   //响应码
    private String message;   //响应消息
    private Map<String,Object> data = new HashMap<>();


    public static R ok(){

        R r = new R();
        r.setCode(0);
        r.setMessage("成功");
        return r;
    }

    public static R error(){

        R r = new R();
        r.setCode(-1);
        r.setMessage("失败");
        return r;
    }


    public R data(String key, Object value){
        this.data.put(key,value);
        return this;
    }
}































