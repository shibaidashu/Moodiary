package com.example.demo.payOrder.Service;

import com.example.demo.common.DTO.OrderDTO;

public interface PayOrderService {

    OrderDTO createPayOrder(Integer userId, Integer productId, Integer quantity);
}
