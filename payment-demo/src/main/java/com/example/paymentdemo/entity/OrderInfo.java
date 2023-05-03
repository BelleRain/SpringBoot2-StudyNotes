package com.example.paymentdemo.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "t_order_info")
public class OrderInfo extends BaseEntity{

    private String title;
    private String orderNo;
    private long userId;
    private long productId;
    private Integer totalFee;  //订单金额（分）
    private String codeUrl;    //订单二维码链接
    private String orderStatus;  //订单状态


}
