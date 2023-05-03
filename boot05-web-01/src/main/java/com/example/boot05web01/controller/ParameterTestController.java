package com.example.boot05web01.controller;


import org.springframework.web.bind.annotation.*;
import sun.management.resources.agent;

import javax.lang.model.element.NestingKind;
import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ParameterTestController {


    //  car/2/owner/zhangsan
    @GetMapping("/car/{id}/owner/{username}")
    public Map<String,Object> getCar(@PathVariable("id") Integer id,
                                     @PathVariable("username") String username,
                                     @PathVariable Map<String,Object> pv,
                                     @RequestHeader("user-agent") String userAgent,
                                     @RequestHeader Map<String,String> mv,
                                     @RequestParam("age") Integer age,
                                     @RequestParam("inters") List<String> inters,
                                     @RequestParam Map<String,String> rv,
                                     @CookieValue("Pycharm-55812772") String ck,
                                     @CookieValue("Pycharm-55812772") Cookie cookie){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("username",username);
        map.put("pv",pv);
        map.put("user-agent",userAgent);
        map.put("mv",mv);
        map.put("age",age);
        map.put("inters",inters);
        map.put("rv",rv);
        map.put("Pycharm-55812772",ck);
        System.out.println("Pycharm-55812772========>" + cookie.getName() + cookie.getValue());
        return map;
    }

    @PostMapping("/save")
    public Map postBody(@RequestBody String content,@RequestParam String userName) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //解码，使中文和特殊字符正常显示
        String result = URLDecoder.decode(content, "utf-8");
        map.put("content",result);
        map.put("userName",userName);
        System.out.println(map);
        return map;
    }

    //矩阵变量必须有url路径变量才能被解析
    // /cars/sell;low=34;brand=byd,audi,yd
    @GetMapping("/cars/{path}")
    public Map carsSell(@MatrixVariable("low") Integer low,
                        @MatrixVariable("brand") List<String> brand,
                        @PathVariable("path") String path){
        Map<String, Object> map = new HashMap<>();
        map.put("low",low);
        map.put("brand",brand);
        map.put("path",path);
        return map;
    }

    // /boss/1;age=20/2;age=10
    @GetMapping("/boss/{bossId}/{empId}")
    public Map boss(@MatrixVariable(value = "age",pathVar = "bossId") Integer bossAge,
                    @MatrixVariable(value = "age",pathVar = "empId") Integer empAge,
                    @PathVariable("bossId") Integer bossId,
                    @PathVariable("empId") Integer empId){
        Map<String, Object> map = new HashMap<>();
        map.put("bossAge",bossAge);
        map.put("empAge",empAge);
        map.put("bossId",bossId);
        map.put("empId",empId);
        return map;
    }

}
