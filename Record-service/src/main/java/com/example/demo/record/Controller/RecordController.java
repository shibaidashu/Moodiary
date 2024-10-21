package com.example.demo.record.Controller;


import cn.hutool.core.convert.Convert;
import com.example.demo.common.DTO.MoodHistoryDTO;
import com.example.demo.common.Utils.Result;
import com.example.demo.common.Utils.ThreadLocalUtil;
import com.example.demo.common.Entity.Record;
import com.example.demo.record.Service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Record")
@Api(tags = "日志记录相关接口")
public class RecordController {
    @Autowired
    RecordService recordService;


    @ApiOperation("记录笔记")
    @PostMapping("/add")
    public Result addRecord(@Validated @RequestBody Record record) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = Convert.toInt(claims.get("id"));
        record.setUserId(userId);
        recordService.addRecord(record);
        return Result.success();
    }


    @ApiOperation("获取心情历史记录")
    @GetMapping("/History")
    public Result getMoodHistory(@RequestParam int queryPeriod){
//        1：当天；2：本周；3：本月。
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = Convert.toInt(claims.get("id"));
        List<MoodHistoryDTO> result = recordService.getMoodHistory(queryPeriod,userId);
        return Result.success(result);
    }
}