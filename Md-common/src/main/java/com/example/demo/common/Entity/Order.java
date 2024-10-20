package com.example.demo.common.Entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Order {

    private Integer orderId;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private Integer totalPoints;
    private Timestamp createdAt;
}
