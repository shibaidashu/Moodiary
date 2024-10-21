package com.example.demo.payOrder.Controller;

import com.example.demo.common.DTO.OrderDTO;
import com.example.demo.common.Utils.Result;
import com.example.demo.common.Utils.ThreadLocalUtil;
import com.example.demo.payOrder.Service.PayOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/pay")
@Api(tags = "支付相关接口")
public class PayOrderController {

    @Autowired
    private PayOrderService payOrderService;

    // 生成支付单并完成支付
    @ApiOperation("生成支付单并扣减积分")
    @PostMapping("/create")
    public Result createPayOrder(@RequestParam Integer productId, @RequestParam Integer quantity) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = Integer.parseInt(claims.get("id").toString());
        log.info("生成支付单：用户ID={}, 商品ID={}, 商品数量={}", userId, productId, quantity);
        OrderDTO order = payOrderService.createPayOrder(userId, productId,quantity);
        return Result.success(order);
    }
}

