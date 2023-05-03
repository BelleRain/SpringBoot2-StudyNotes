package com.example.paymentdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.paymentdemo.entity.OrderInfo;
import com.example.paymentdemo.enums.OrderStatus;

import java.util.List;

public interface OrderInfoService extends IService<OrderInfo> {

    //生成订单存入数据库
    OrderInfo createOrderByProductId(Long productId);
    //存储二维码链接
    void saveCodeUrl(String orderNo,String codeUrl);

    List<OrderInfo> listOrderByCreateTimeDesc();

    void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus);

    String getOrderStatus(String orderNo);
}


























