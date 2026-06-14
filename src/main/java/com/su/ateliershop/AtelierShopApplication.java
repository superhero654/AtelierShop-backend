package com.su.ateliershop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.su.ateliershop.mapper")
public class AtelierShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtelierShopApplication.class, args);
    }

}
