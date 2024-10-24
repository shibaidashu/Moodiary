package com.example.demo.payOrder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.example.demo.payOrder.Mapper")
@ComponentScan(basePackages = {"com.example.demo.payOrder", "com.example.demo.common"})
@SpringBootApplication
public class PayOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayOrderApplication.class, args);
    }
}