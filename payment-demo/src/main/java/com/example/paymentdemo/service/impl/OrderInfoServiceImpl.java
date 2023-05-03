package com.example.paymentdemo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.paymentdemo.entity.OrderInfo;
import com.example.paymentdemo.entity.Product;
import com.example.paymentdemo.enums.OrderStatus;
import com.example.paymentdemo.mapper.OrderInfoMapper;
import com.example.paymentdemo.mapper.ProductMapper;
import com.example.paymentdemo.service.OrderInfoService;
import com.example.paymentdemo.util.OrderNoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Slf4j
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {


    @Resource
    private ProductMapper productMapper;

    /**
     * 通过商品id生成订单
     * @param productId
     * @return
     */
    @Override
    public OrderInfo createOrderByProductId(Long productId) {

        OrderInfo orderInfo = this.getNoPayOrderByProductId(productId);
        //判断订单是否生成且未支付
        if(orderInfo!= null){
            return orderInfo;
        }
        //获取商品信息
        Product product = productMapper.selectById(productId);

        //生成订单
        orderInfo = new OrderInfo();
        orderInfo.setTitle(product.getTitle());
        orderInfo.setOrderNo(OrderNoUtils.getOrderNo());
        orderInfo.setProductId(productId);
        orderInfo.setTotalFee(product.getPrice());  //分
        orderInfo.setOrderStatus(OrderStatus.NOTPAY.getType());

        //存入数据库
        baseMapper.insert(orderInfo);


        return orderInfo;
    }

    /**
     * 存储生成的二维码链接
     * @param orderNo
     * @param codeUrl
     */
    @Override
    public void saveCodeUrl(String orderNo, String codeUrl)
    {
        UpdateWrapper<OrderInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_no",orderNo).set("code_url",codeUrl);
        //OrderInfo orderInfo = new OrderInfo();
        //orderInfo.setCodeUrl(codeUrl);
        baseMapper.update(null,updateWrapper);
    }

    /**
     *查询订单列表，并倒叙查询
     * @return
     */
    @Override
    public List<OrderInfo> listOrderByCreateTimeDesc() {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        List<OrderInfo> selectList = baseMapper.selectList(queryWrapper);
        return selectList;
    }

    /**
     * 根据订单号更改支付状态
     * @param orderNo
     * @param orderStatus
     */
    @Override
    public void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus) {
        UpdateWrapper<OrderInfo> updateWrapper= new UpdateWrapper<>();
        updateWrapper.eq("order_no",orderNo).set("order_status",orderStatus.getType());
        baseMapper.update(null,updateWrapper);
    }

    /**
     * 根据订单号获取订单状态
     * @param orderNo
     * @return
     */
    @Override
    public String getOrderStatus(String orderNo) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderNo);
        OrderInfo orderInfo = baseMapper.selectOne(queryWrapper);
        if (orderInfo==null){
            return null;
        }
        return orderInfo.getOrderStatus();
    }

    /**
     *根据id查询未支付订单
     * 防止重复创建对象
     * @param productId
     * @return
     */
    public OrderInfo getNoPayOrderByProductId(Long productId){
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id",productId);
        queryWrapper.eq("order_status",OrderStatus.NOTPAY.getType());
        OrderInfo orderInfo = baseMapper.selectOne(queryWrapper);
        return orderInfo;
    }
}
