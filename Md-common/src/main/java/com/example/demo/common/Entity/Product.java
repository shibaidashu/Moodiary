package com.example.demo.common.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int productId;
    private String productName;
    private String productDescription;
    private int pointsCost;
    private int stock;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
