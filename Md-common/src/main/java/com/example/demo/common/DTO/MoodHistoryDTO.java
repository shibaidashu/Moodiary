package com.example.demo.common.DTO;

import cn.hutool.json.JSON;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MoodHistoryDTO {
    int userId;
    int recordId;
    String mood;
}
