package com.example.demo.record.Mapper;

import com.example.demo.common.DTO.IntensityDTO;
import com.example.demo.common.DTO.MoodHistoryDTO;
import com.example.demo.common.DTO.PointDTO;
import com.example.demo.common.DTO.RecordIntensityDTO;
import com.example.demo.common.Entity.Record;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface RecordMapper {

    @Insert("INSERT INTO record(userId, Title, Content) VALUES(#{userId}, #{title}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "recordId")
    public void addRecord(Record record);

    List<MoodHistoryDTO> getMoodHistory(@Param("type") int type, @Param("userId") Integer userId);

    @Update("UPDATE record SET Mood = #{jsonData}, TopEmotion = #{topEmotion}, Comfort = #{comfortLanguage}, Guidance = #{behavioralGuidance} WHERE recordId = #{recordId}")
    void setIntensity(String jsonData, String topEmotion,String comfortLanguage,String behavioralGuidance,int recordId);

    List<RecordIntensityDTO>getRecordIntensity(int type, int userId);

    // 根据用户ID查询用户积分DTO
    @Select("select * from points where userId = #{userId}")
    PointDTO selectPointDTOByUserId(int userId);

    // 更新用户积分信息
    @Update("update points set pointsBalance = #{userPointsBalance} WHERE userId = #{userId} ")
    int updatePointsBalance(int userPointsBalance, int userId);
}