package com.zxw.microshop.storage.feign;

import com.zxw.microshop.storage.api.StorageApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author wuhongyun
 * @date 2020/6/3 0:04
 */
@FeignClient(name = "micro-shop-storage")
public interface StorageClient extends StorageApi {

}
