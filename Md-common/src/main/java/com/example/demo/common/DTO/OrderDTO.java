package com.example.demo.common.DTO;

import lombok.Data;

@Data
public class OrderDTO {
    private Integer orderId;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private Integer totalPoints;
}
