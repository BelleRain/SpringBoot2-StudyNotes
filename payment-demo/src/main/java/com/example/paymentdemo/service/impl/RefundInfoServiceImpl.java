package com.example.paymentdemo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.paymentdemo.entity.RefundInfo;
import com.example.paymentdemo.mapper.RefundInfoMapper;
import com.example.paymentdemo.service.RefundInfoService;
import org.springframework.stereotype.Service;


@Service
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoMapper, RefundInfo> implements RefundInfoService {
}
