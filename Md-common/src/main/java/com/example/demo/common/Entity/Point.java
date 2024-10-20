package com.example.demo.common.Entity;

import lombok.Data;

import java.security.Timestamp;

@Data
public class Point {

    private Integer pointsId;
    private Integer userId;
    private Integer pointsBalance = 0;
    private Timestamp updatedAt;

}