package com.zxw.microshop.storage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.zxw")
@MapperScan("com.zxw.microshop.storage.mapper")
public class MicroShopStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroShopStorageApplication.class, args);
    }

}
