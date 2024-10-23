package com.example.demo.user.Impl;

import com.example.demo.common.DTO.PointsTransactionDTO;
import com.example.demo.common.Entity.User;
import com.example.demo.user.Mapper.UserMapper;
import com.example.demo.user.Service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "points.exchange";

    private static final String ROUTING_KEY = "points.routingkey";

    @Override
    public void addUser(String username, String password, String email, String gender, String status, byte[] avator) {
        userMapper.addUser(username, password, email, gender, status, avator);
    }

    @Override
    public User getUser(int id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public void initiateScore(int userId) {
        userMapper.initiatePoints(userId);
        PointsTransactionDTO transaction = new PointsTransactionDTO();
        transaction.setUserId(userId);
        transaction.setChangeAmount(800);
        transaction.setTransactionType("Earn");
        transaction.setDescription("Record Reward");
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, transaction);
    }
}
