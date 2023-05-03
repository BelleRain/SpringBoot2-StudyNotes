package com.example.paymentdemo.controller;


import com.example.paymentdemo.entity.Product;
import com.example.paymentdemo.service.ProductService;
import com.example.paymentdemo.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@CrossOrigin  //开启前端的跨域访问
@Api(tags = "商品管理")
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("测试接口")
    @GetMapping("/test")
    public R test() {
        return R.ok().data("message","成功").data("time",new Date());
    }



    @ApiOperation("商品列表")
    @GetMapping("/list")
    public R list(){

        List<Product> list = productService.list();
        return R.ok().data("productList",list);
    }



}










































