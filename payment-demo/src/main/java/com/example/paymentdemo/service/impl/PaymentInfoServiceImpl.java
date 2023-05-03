package com.example.paymentdemo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.paymentdemo.entity.PaymentInfo;
import com.example.paymentdemo.enums.PayType;
import com.example.paymentdemo.enums.wxpay.WxApiType;
import com.example.paymentdemo.mapper.PaymentInfoMapper;
import com.example.paymentdemo.service.PaymentInfoService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {
    /**
     * 根据明文记录支付日志
     * @param plainText
     */
    @Override
    public void createPaymentInfo(String plainText) {
        log.info("记录支付日志");
        PaymentInfo paymentInfo = new PaymentInfo();
        Gson gson = new Gson();
        HashMap plainTextMap = gson.fromJson(plainText, HashMap.class);

        //商户订单号
        String orderNo = (String)plainTextMap.get("out_trade_no");
        //微信支付订单号
        String transactionId = (String)plainTextMap.get("transaction_id");
        //用户支付金额
        Map<String,Object> amount = (Map)plainTextMap.get("amount");
        //隐式转换double类型报错
        Integer payerTotal = ((Double)amount.get("payer_total")).intValue();
        //交易类型
        String tradeType = (String)plainTextMap.get("trade_type");
        //交易状态
        String tradeState = (String)plainTextMap.get("trade_state");

        paymentInfo.setOrderNo(orderNo);
        paymentInfo.setTransactionId(transactionId);
        paymentInfo.setPaymentType(PayType.WXPAY.getType());
        paymentInfo.setPayerTotal(payerTotal);
        paymentInfo.setTradeType(tradeType);
        paymentInfo.setTradeState(tradeState);
        //json型所有的交易信息
        paymentInfo.setContent(plainText);

        baseMapper.insert(paymentInfo);
    }
}





































































