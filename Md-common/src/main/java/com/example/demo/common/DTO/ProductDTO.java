package com.example.demo.common.DTO;

import lombok.Data;

@Data
public class ProductDTO {
    private Integer productId;
    private String productName;
    private String productDescription;
    private Integer pointsCost;
    private Integer stock;
}
