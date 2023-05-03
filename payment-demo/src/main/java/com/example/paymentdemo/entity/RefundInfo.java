package com.example.paymentdemo.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.service.ApiListing;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_refund_info")
public class RefundInfo extends BaseEntity{

    private String orderNo;
    private String refundNo;
    private String refundId;
    private Integer totalFee;
    private Integer refund;
    private String reason;
    private String refund_status;
    private String content_return;
    private String content_notify;
}
