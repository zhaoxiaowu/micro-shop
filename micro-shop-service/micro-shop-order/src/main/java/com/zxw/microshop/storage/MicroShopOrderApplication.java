package com.zxw.microshop.storage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.zxw.microshop.order.mapper")
@EnableFeignClients
public class MicroShopOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroShopOrderApplication.class, args);
    }

}
