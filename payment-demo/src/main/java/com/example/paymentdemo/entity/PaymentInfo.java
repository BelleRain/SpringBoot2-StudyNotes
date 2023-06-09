package com.example.paymentdemo.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_payment_info")
public class PaymentInfo extends BaseEntity{

    private String orderNo;
    private String transactionId;  //支付系统交易编号
    private String paymentType;  //支付类型
    private String tradeType;    //交易类型
    private String tradeState;   //交易状态
    private Integer payerTotal;  //支付金额
    private String content;      //通知参数
}
