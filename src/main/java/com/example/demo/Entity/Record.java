package com.example.demo.Entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Record {
    private Integer recordId;
    private Integer userId;
    private String title;
    private String content;
    private Integer mood;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}