package com.example.paymentdemo.service;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

public interface WxpayService {
    Map<String, Object> nativePay(Long productId) throws Exception;

    void proessOrder(Map<String, Object> bodyMap) throws GeneralSecurityException;

    void cancelOrder(String orderNo) throws Exception;
}
