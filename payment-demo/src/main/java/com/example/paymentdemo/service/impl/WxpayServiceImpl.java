package com.example.paymentdemo.service.impl;

import com.example.paymentdemo.config.WxpayConfig;
import com.example.paymentdemo.entity.OrderInfo;
import com.example.paymentdemo.enums.OrderStatus;
import com.example.paymentdemo.enums.wxpay.WxApiType;
import com.example.paymentdemo.enums.wxpay.WxNotifyType;
import com.example.paymentdemo.service.OrderInfoService;
import com.example.paymentdemo.service.PaymentInfoService;
import com.example.paymentdemo.service.WxpayService;
import com.example.paymentdemo.util.OrderNoUtils;
import com.google.gson.Gson;
import com.sun.xml.internal.bind.api.impl.NameConverter;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class WxpayServiceImpl implements WxpayService {


    @Autowired
    private WxpayConfig wxpayConfig;

    @Autowired
    private CloseableHttpClient wxPayClient;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private PaymentInfoService paymentInfoService;

    private final ReentrantLock lock = new ReentrantLock();



    public Map<String, Object> nativePay(Long productId) throws Exception {


        log.info("生成订单");

        OrderInfo orderInfo = orderInfoService.createOrderByProductId(productId);
        String codeUrl = orderInfo.getCodeUrl();
        if (orderInfo !=null  && !StringUtils.isEmpty(codeUrl)){
            log.info("订单已存在，二维码已保存");
            //返回二维码
            Map<String, Object> map = new HashMap();
            map.put("codeUrl",codeUrl);
            map.put("orderNo",orderInfo.getOrderNo());
            return map;
        }

        //调用统一下单api
        HttpPost httpPost = new HttpPost(wxpayConfig.getDomain().concat(WxApiType.NATIVE_PAY.getType()));
        // 请求body参数
        Gson gson = new Gson();
        Map paramMaps = new HashMap();

        paramMaps.put("appid",wxpayConfig.getAppid());
        paramMaps.put("mchid",wxpayConfig.getMchId());
        paramMaps.put("description",orderInfo.getTitle());
        paramMaps.put("out_trade_no",orderInfo.getOrderNo());
        paramMaps.put("notify_url",wxpayConfig.getNotifyDomain().concat(WxNotifyType.NATIVE_NOTIFY.getType()));
        Map amountMap = new HashMap();
        amountMap.put("total",orderInfo.getTotalFee());
        amountMap.put("currency","CNY");
        paramMaps.put("amount",amountMap);

        //将参数转换为Json字符串
        String jsonParams = gson.toJson(paramMaps);

        log.info("请求参数 ===> {}" + jsonParams);

        //将请求参数设置到请求对象中
        StringEntity entity = new StringEntity(jsonParams,"utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");

        //完成签名并执行请求
        CloseableHttpResponse response = wxPayClient.execute(httpPost);

        try {
            String bodyAsString = EntityUtils.toString(response.getEntity());  //响应体
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) { //处理成功
                log.info("成功,返回响应体 = " + bodyAsString);
            } else if (statusCode == 204) { //处理成功，无返回Body
                log.info("成功");
            } else {
                log.info("失败,响应码 = " + statusCode+ ",返回响应体 = " + bodyAsString);
                throw new IOException("request failed");
            }

            //响应结果
            Map<String,String> resultMap = gson.fromJson(bodyAsString, HashMap.class);
            //二维码
            codeUrl = resultMap.get("code_url");

            //存储二维码链接
            String orderNo = orderInfo.getOrderNo();
            orderInfoService.saveCodeUrl(orderNo,codeUrl);

            //返回二维码
            Map<String, Object> map = new HashMap();
            map.put("codeUrl",codeUrl);
            map.put("orderNo",orderInfo.getOrderNo());

            return map;

        } finally {
            response.close();
        }

    }

    @Override
    public void proessOrder(Map<String, Object> bodyMap) throws GeneralSecurityException {

        log.info("处理订单");
        String plainText = decryptFromResource(bodyMap);

        //将明文转换为map
        Gson gson = new Gson();
        HashMap plainTextMap = gson.fromJson(plainText, HashMap.class);
        String orderNo = (String)plainTextMap.get("out_trade_no");

         /*在对业务数据进行状态检查和处理之前，
        要采用数据锁进行并发控制，
        以避免函数重入造成的数据混乱*/
        //尝试获取锁：
        // 成功获取则立即返回true，获取失败则立即返回false。不必一直等待锁的释放
        if(lock.tryLock()){
            try {
                //处理重复通知
                //接口调用的幂等性，无论调用多少次，产生的结果都是一样的
                //如果订单状态不等于未支付，则直接返回；否则，更改支付状态并记录支付日志
                String orderStatus =  orderInfoService.getOrderStatus(orderNo);
                if(!orderStatus.equals(OrderStatus.NOTPAY.getType())){
                    return;
                }
                //模拟通知并发
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //根据订单号更改支付状态
                orderInfoService.updateStatusByOrderNo(orderNo,OrderStatus.SUCCESS);

                //记录支付日志
                paymentInfoService.createPaymentInfo(plainText);
            } finally {
                lock.unlock();
                //主动释放锁
            }
        }
    }


    @Override
    public void cancelOrder(String orderNo) throws Exception {
        //调用微信支付关单接口
        this.closeOrder(orderNo);
        //更新商户订单状态
        orderInfoService.updateStatusByOrderNo(orderNo,OrderStatus.CANCEL);
    }

    /**
     * 微信支付关单接口
     * @param orderNo
     */
    private void closeOrder(String orderNo) throws IOException {
        log.info("关单接口的调用，订单号 ===> {}",orderNo);

        //创建远程请求对象
        String url = String.format(WxApiType.CLOSE_ORDER_BY_NO.getType(),orderNo);
        url = wxpayConfig.getDomain().concat(url);
        HttpPost httpPost = new HttpPost(url);

        //组装json请求体
        Gson gson = new Gson();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("mchid",wxpayConfig.getMchId());
        String jsonParams = gson.toJson(paramsMap);
        log.info("请求参数 ===> {}",jsonParams);

        //将请求参数设置到请求对象中
        StringEntity entity = new StringEntity(jsonParams,"utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");

        //完成签名并执行请求
        CloseableHttpResponse response = wxPayClient.execute(httpPost);

        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) { //处理成功
                log.info("成功");
            } else if (statusCode == 204) { //处理成功，无返回Body
                log.info("成功");
            } else {
                log.info("Native下单失败,响应码 = " + statusCode);
                throw new IOException("request failed");
            }
        }finally {
            response.close();
        }
    }

    private String decryptFromResource(Map<String, Object> bodyMap) throws GeneralSecurityException {
        log.info("密文解密");
        Map<String,String> resourceMap = (Map) bodyMap.get("resource");
        //附加数据
        String associatedData = resourceMap.get("associated_data");
        //数据密文
        String ciphertext = resourceMap.get("ciphertext");
        //随机串
        String nonce = resourceMap.get("nonce");
        log.info("密文 ===> {}", ciphertext);
        AesUtil aesUtil = new AesUtil(wxpayConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8));
        String plainText = aesUtil.decryptToString(associatedData.getBytes(StandardCharsets.UTF_8),
                nonce.getBytes(StandardCharsets.UTF_8),
                ciphertext);
        log.info("明文 ====> {}",plainText);
        return plainText;
    }
}





































