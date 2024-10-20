package com.example.demo.transaction.Mapper;

import com.example.demo.common.DTO.PointsTransactionDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionMapper {
    // 插入积分交易记录
    @Insert("INSERT INTO PointsTransaction (userId, changeAmount, transactionType, description) " +
            "VALUES (#{userId}, #{changeAmount}, #{transactionType}, #{description})")
    int insertTransaction(PointsTransactionDTO transaction);
}
