package com.example.demo.common.Entity;

import lombok.Data;

import java.security.Timestamp;

@Data
public class PointsTransaction {

    private Integer transactionId;
    private Integer userId;
    private Integer changeAmount;
    private TransactionType transactionType;
    private String description;
    private Timestamp createdAt;

    public enum TransactionType {
        EARN, SPEND
    }
}
