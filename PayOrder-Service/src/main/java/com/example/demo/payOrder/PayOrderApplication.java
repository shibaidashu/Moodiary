package com.example.demo.payOrder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.demo.payOrder.Mapper")
@SpringBootApplication
public class PayOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayOrderApplication.class, args);
    }
}