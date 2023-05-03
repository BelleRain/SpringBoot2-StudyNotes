package com.example.admin.service.Impl;


import com.example.admin.bean.Product;
import com.example.admin.mapper.ProductMapper;
import com.example.admin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    public Product getProductById(Long id){
        return productMapper.getProductById(id);
    }
}
