package com.example.demo.payOrder.Mapper;

import com.example.demo.common.DTO.PointDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PointsMapper {

    // 根据用户ID查询用户积分DTO
    @Select("select * from points where userId = #{userId}")
    PointDTO selectPointDTOByUserId(int userId);

    // 更新用户积分信息
    @Update("update points set pointsBalance = #{userPointsBalance} WHERE userId = #{userId} ")
    int updatePointsBalance(int userPointsBalance, int userId);
}
