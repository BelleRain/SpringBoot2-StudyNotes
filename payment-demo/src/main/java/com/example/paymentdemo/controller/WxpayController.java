package com.example.paymentdemo.controller;


import com.example.paymentdemo.service.WxpayService;
import com.example.paymentdemo.util.HttpUtils;
import com.example.paymentdemo.util.WechatPay2ValidatorForRequest;
import com.example.paymentdemo.vo.R;
import com.google.common.base.Verify;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.xml.internal.bind.v2.TODO;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@Api(tags = "网站微信支付APIv3")
@RequestMapping("/api/wx-pay")
@Slf4j
public class WxpayController {

    @Resource
    private WxpayService wxpayService;

    @Resource
    private Verifier verifier;

    @ApiOperation("调用统一下单API，生成支付二维码")
    @PostMapping("/native/{productId}")
       public R nativePay(@PathVariable Long productId) throws Exception {

        log.info("发起支付请求");

        //返回支付二维码链接和订单编号
        Map<String,Object> map =  wxpayService.nativePay(productId);
        return R.ok().setData(map);
    }

    //微信pay自动向该接口发起回调
    @PostMapping("/native/notify")
    public String nativeNotify(HttpServletRequest request, HttpServletResponse response){
        Gson gson = new Gson();
        //创建应答对象
        Map<String, String> map = new HashMap<>();

        //处理通知参数
        try {
            String body = HttpUtils.readData(request);
            Map<String,Object> bodyMap = gson.fromJson(body, HashMap.class);
            String requestId = (String)bodyMap.get("id");
            log.info("通知支付的id ====> {}",requestId);
            //log.info("通知的完整数据 ====>{}",bodyMap);

            //验签
            WechatPay2ValidatorForRequest wechatPay2ValidatorForRequest =
                    new WechatPay2ValidatorForRequest(verifier,requestId,body);
            if (!wechatPay2ValidatorForRequest.validate(request)) {
                log.error("通知验签失败");

                response.setStatus(500);
                map.put("code","ERROR");
                map.put("message","通知验签失败");
                return gson.toJson(map);
            }
            log.info("验签成功");

            //订单的处理
            wxpayService.proessOrder(bodyMap);


            //应答超时
            //模拟微信接收端的重复通知
            //TimeUnit.SECONDS.sleep(5);


            //成功应答
            response.setStatus(200);
            map.put("code","SUCCESS");
            map.put("message","成功");
            return gson.toJson(map);

        } catch (Exception e) {
            e.printStackTrace();
            //失败应答
            response.setStatus(500);
            map.put("code","ERROR");
            map.put("message","失败");
            return gson.toJson(map);
        }

    }


    /**
     * 用户取消订单
     * @param orderNo
     * @return
     */
    @ApiOperation("用户取消订单")
    @PostMapping("/cancel/{orderNo}")
    public R cancel(@PathVariable String orderNo) throws Exception {
        log.info("取消订单");
        wxpayService.cancelOrder(orderNo);
        return R.ok().setMessage("订单已取消");
    }

}














































