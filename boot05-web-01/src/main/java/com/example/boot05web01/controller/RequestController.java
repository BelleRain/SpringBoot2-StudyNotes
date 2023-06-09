package com.example.boot05web01.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RequestController {


    @GetMapping("/gotoPage")
    public String goTo(HttpServletRequest request){
        request.setAttribute("msg","成功了....");
        request.setAttribute("code",200);
        return "forward:/success";  //转发到 /success
    }

    @GetMapping("/params")
    public String testParam(Map map,Model model,
                            HttpServletRequest request,
                            HttpServletResponse response){
        map.put("hello","world666");
        model.addAttribute("world","hello666");
        request.setAttribute("message","成功了。。。");
        Cookie cookie = new Cookie("c1","c1");
        response.addCookie(cookie);
        return "forward:/success";
    }

    @ResponseBody
    @GetMapping("/success")
    public Map success(@RequestAttribute(value = "msg",required =false) String msg,
                       @RequestAttribute(value = "code",required = false) Integer code,
                       HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        Object hello = request.getAttribute("hello");
        Object world = request.getAttribute("world");
        Object message = request.getAttribute("message");
        map.put("hello",hello);
        map.put("world",world);
        map.put("message",message);

        Object msg1 = request.getAttribute("msg");
        Object code1 = request.getAttribute("code");
        map.put("reqMethod",msg1);
        map.put("reqcode",code1);
        map.put("annotationMethod",msg);
        map.put("annotationCode",code);
        return map;
    }

}
