package com.example.demo.record.Mapper;

import com.example.demo.common.DTO.IntensityDTO;
import com.example.demo.common.DTO.MoodHistoryDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface RecordMapper {

    @Insert("INSERT INTO record(userId, Title, Content) VALUES(#{userId},#{title},#{content})")
    public void addRecord(int userId, String title, String content);

    List<MoodHistoryDTO>getMoodHistory(int type, int userId);

    List<IntensityDTO>getIntensity(int type, int userId);
}