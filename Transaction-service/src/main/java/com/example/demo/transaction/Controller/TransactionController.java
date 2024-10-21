package com.example.demo.transaction.Controller;

import com.example.demo.common.Entity.PageBean;
import com.example.demo.common.Utils.Result;
import com.example.demo.common.Utils.ThreadLocalUtil;
import com.example.demo.transaction.Service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/transaction")
@Api(tags = "交易订单相关接口")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // 创建积分交易记录
    @ApiOperation("创建积分交易记录")
    @PostMapping("/create")
    public Result createTransaction(
            @RequestParam Integer changeAmount,
            @RequestParam String transactionType,
            @RequestParam String description) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        int userId = (int)claims.get("id");
        log.info("创建交易记录：userId={}, changeAmount={}, transactionType={}", userId, changeAmount, transactionType);
        transactionService.createTransaction(userId, changeAmount, transactionType, description);
        return Result.success();
    }


    @ApiOperation("获取积分交易记录")
    @GetMapping("/history")
    public Result getTransactionHistory(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer pageSize){
        Map<String, Object>claims = ThreadLocalUtil.get();
        int userId = (int)claims.get("id");
        PageBean pageBean = transactionService.page(userId,page, pageSize);
        //响应
        return Result.success(pageBean);

    }
}
