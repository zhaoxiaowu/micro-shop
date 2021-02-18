package com.zxw.microshop.storage.controller;

import com.zxw.microshop.storage.service.StorageService;
import com.zxw.microshop.storage.api.StorageApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuhongyun
 * @date 2020/6/2 15:40
 */
@RestController
public class StorageController implements StorageApi {

    @Autowired
    private StorageService storageService;

    @Override
    public void reduceStorage(String productCode, Integer count) throws Exception{
        storageService.reduceStorage(productCode,count);
    }
}
