package com.zxw.microshop.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxw.microshop.storage.model.Order;

/**
 * @author wuhongyun
 * @date 2020/6/2 15:47
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     *
     * @param userId
     * @param produceCode
     * @param count
     * @return
     */
    boolean createOrder(String userId, String produceCode, Integer count);
}
