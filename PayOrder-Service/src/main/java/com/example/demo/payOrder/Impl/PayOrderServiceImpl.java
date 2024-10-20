package com.example.demo.payOrder.Impl;


import com.example.demo.common.DTO.OrderDTO;
import com.example.demo.common.DTO.PointDTO;
import com.example.demo.common.DTO.PointsTransactionDTO;
import com.example.demo.common.DTO.ProductDTO;
import com.example.demo.payOrder.Mapper.PayOrderMapper;
import com.example.demo.payOrder.Mapper.PointsMapper;
import com.example.demo.payOrder.Mapper.ProductsMapper;
import com.example.demo.payOrder.Service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PayOrderServiceImpl implements PayOrderService {

    @Autowired
    private PointsMapper pointsMapper;

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "points.exchange";

    private static final String ROUTING_KEY = "points.routingkey";

    @Transactional
    public OrderDTO createPayOrder(Integer userId, Integer productId, Integer quantity) {
        // 1. 查询商品信息
        ProductDTO product = productsMapper.selectProductDTOById(productId);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        } else if (product.getStock() < quantity) {
            throw new RuntimeException("商品库存不足");
        }

        // 2. 查询用户积分信息
        PointDTO userPoints = pointsMapper.selectPointDTOByUserId(userId);
        if (userPoints == null || userPoints.getPointsBalance() < (product.getPointsCost())*quantity) {
            throw new RuntimeException("用户积分不足");
        }

        //3. 扣减商品库存
        int productsStock = product.getStock() - quantity;
        productsMapper.setProductsStock(productsStock,productId);

        // 4. 扣减用户积分
        userPoints.setPointsBalance(userPoints.getPointsBalance() - (product.getPointsCost())*quantity);
        int userPointsBalance = userPoints.getPointsBalance();
        pointsMapper.updatePointsBalance(userPointsBalance,userId);

        // 5. 创建订单
        OrderDTO order = new OrderDTO();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setTotalPoints((product.getPointsCost())*quantity);

        // 6. 保存订单
        payOrderMapper.insertOrder(order);

        // 7.构建 PointsTransactionDTO 并发送到消息队列
        PointsTransactionDTO transaction = new PointsTransactionDTO();
        transaction.setUserId(userId);
        transaction.setChangeAmount(-product.getPointsCost() * quantity); // 消耗积分
        transaction.setTransactionType("Spend");
        transaction.setDescription("购买商品");

        // 发送消息到 RabbitMQ
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, transaction);

        return order;  // 返回生成的订单信息
        }
}
