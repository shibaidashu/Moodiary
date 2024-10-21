package com.example.demo.record.Impl;

import cn.hutool.json.JSONUtil;
import com.example.demo.common.DTO.IntensityDTO;
import com.example.demo.common.DTO.MoodHistoryDTO;
import com.example.demo.common.Entity.Record;
import com.example.demo.record.Mapper.RecordMapper;
import com.example.demo.record.Service.RecordService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService{

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    private static final String EXCHANGE = "points.exchange";

    private static final String ROUTING_KEY = "record.routingkey";


    @Override
    public List<MoodHistoryDTO> getMoodHistory(int type, int userId) {
        return recordMapper.getMoodHistory(type, userId);
    }

    @RabbitListener(queues = "callback.queue")
    @Transactional
    public void getIntensity(IntensityDTO intensityDTO) {
        // intensityDTO 将自动从消息中反序列化
        String jsonData = JSONUtil.toJsonStr(intensityDTO.getData());
        jsonData = jsonData.substring(1, jsonData.length() - 1);
        int recordId = intensityDTO.getRecord_Id();
        System.out.println("接收到的记录 ID: " + recordId);
        System.out.println("情绪数据: " + jsonData);
        recordMapper.setIntensity(jsonData,recordId);
    }

    @Override
    public void addRecord(Record record) {
        recordMapper.addRecord(record.getUserId(),record.getTitle(),record.getContent());
        // 发送消息到 RabbitMQ
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, record);

    }
}

