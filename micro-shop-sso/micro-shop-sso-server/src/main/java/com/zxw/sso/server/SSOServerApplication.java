package com.zxw.sso.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zxw"})
public class SSOServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(SSOServerApplication.class, args);
    }

}
