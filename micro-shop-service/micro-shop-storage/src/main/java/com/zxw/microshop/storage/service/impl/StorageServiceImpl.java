package com.zxw.microshop.storage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxw.microshop.storage.mapper.StorageMapper;
import com.zxw.microshop.storage.model.Storage;
import com.zxw.microshop.storage.service.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wuhongyun
 * @date 2020/6/2 15:48
 */
@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements StorageService {


    @Override
    @Transactional
    public void reduceStorage(String productCode, Integer count) throws Exception {
        Storage storage = baseMapper.selectOne(new QueryWrapper<Storage>().eq("produce_code", productCode));
        if (storage.getCount() < count) {
            throw new Exception("超过库存数，扣除失败！");
        }
        storage.setCount(storage.getCount() - count);
        baseMapper.updateById(storage);
    }
}
