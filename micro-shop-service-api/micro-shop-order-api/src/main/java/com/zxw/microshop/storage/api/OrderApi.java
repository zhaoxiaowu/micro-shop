package com.zxw.microshop.storage.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wuhongyun
 * @date 2020/6/2 15:18
 */
@Api(value = "订单服务")
@RequestMapping(value = "order")
public interface OrderApi {

    @ApiOperation(value = "创建订单")
    @PostMapping(path = "create")
    boolean createOrder(String userId, String productCode, Integer count);
}
