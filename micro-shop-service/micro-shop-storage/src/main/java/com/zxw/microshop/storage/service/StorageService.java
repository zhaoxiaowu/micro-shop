package com.zxw.microshop.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxw.microshop.storage.model.Storage;

/**
 * @author wuhongyun
 * @date 2020/6/2 15:47
 */
public interface StorageService extends IService<Storage> {

    /**
     * 创建订单
     *
     * @param produceCode
     * @param count
     * @return
     */
    void reduceStorage(String produceCode, Integer count) throws Exception;
}
