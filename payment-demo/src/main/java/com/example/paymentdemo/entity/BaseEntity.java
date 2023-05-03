package com.example.paymentdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {


    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Date createTime;
    private Date updateTime;
}
