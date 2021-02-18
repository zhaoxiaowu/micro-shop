package com.zxw.microshop.storage.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuhongyun
 * @date 2020/6/2 16:02
 */
@Api(value = "库存服务")
@RequestMapping(value = "storage")
public interface StorageApi {

    @ApiOperation(value = "减库存")
    @GetMapping(path = "reduce")
    void reduceStorage(@RequestParam("productCode") String productCode, @RequestParam("count") Integer count) throws Exception;
}
