package com.zxw.microshop.storage.controller;

import com.zxw.microshop.storage.api.OrderApi;
import com.zxw.microshop.storage.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuhongyun
 * @date 2020/6/2 15:40
 */
@RestController
public class OrderController implements OrderApi {

    @Autowired
    private OrderService orderService;

    @Override
    public boolean createOrder(String userId, String productCode, Integer count) {
        return orderService.createOrder(userId, productCode, count);
    }
}
