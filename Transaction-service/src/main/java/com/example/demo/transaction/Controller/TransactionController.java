package com.example.demo.transaction.Controller;

import com.example.demo.common.Utils.Result;
import com.example.demo.transaction.Service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Result createTransaction(@RequestParam Integer userId,
                                    @RequestParam Integer changeAmount,
                                    @RequestParam String transactionType,
                                    @RequestParam String description) {
        log.info("创建交易记录：userId={}, changeAmount={}, transactionType={}", userId, changeAmount, transactionType);
        transactionService.createTransaction(userId, changeAmount, transactionType, description);
        return Result.success();
    }
}
