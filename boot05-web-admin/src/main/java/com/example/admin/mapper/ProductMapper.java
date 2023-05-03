package com.example.admin.mapper;


import com.example.admin.bean.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

    public Product getProductById(Long id);
}
