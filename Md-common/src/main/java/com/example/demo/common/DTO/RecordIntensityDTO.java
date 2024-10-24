package com.example.demo.common.DTO;

import lombok.Data;

import java.sql.Date;

@Data
public class RecordIntensityDTO {
    Date date;
    int postCount;

}