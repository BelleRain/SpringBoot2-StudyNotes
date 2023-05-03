package com.example.paymentdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.paymentdemo.entity.PaymentInfo;

public interface PaymentInfoService extends IService<PaymentInfo> {
    void createPaymentInfo(String plainText);
}
