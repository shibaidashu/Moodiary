package com.example.demo.common.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    private Integer recordId;
    private Integer userId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private Integer mood;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @JsonIgnore
    List<Photo> photoList;
}

