package com.example.paymentdemo.controller;


import com.example.paymentdemo.config.WxpayConfig;
import com.example.paymentdemo.vo.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试控制器")
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private WxpayConfig wxpayConfig;

    @GetMapping
    public R getWxpayConfig(){
        String mchId = wxpayConfig.getMchId();
        String mchSerialNo = wxpayConfig.getMchSerialNo();
        return R.ok().data("mchId",mchId).data("mchSerialNo",mchSerialNo);
    }






}
