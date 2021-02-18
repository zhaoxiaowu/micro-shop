package com.zxw.microshop.storage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxw.microshop.storage.feign.StorageClient;
import com.zxw.microshop.storage.mapper.OrderMapper;
import com.zxw.microshop.storage.model.Order;
import com.zxw.microshop.storage.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


/**
 * @author wuhongyun
 * @date 2020/6/2 15:48
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private StorageClient storageClient;

    @Override
    @GlobalTransactional
    public boolean createOrder(String userId, String productCode, Integer count) {
        BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));
        Order order = new Order()
                .setUserId(userId)
                .setProduceCode(productCode)
                .setCount(count)
                .setMoney(orderMoney);
        baseMapper.insert(order);
        try {
            storageClient.reduceStorage(productCode, count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
