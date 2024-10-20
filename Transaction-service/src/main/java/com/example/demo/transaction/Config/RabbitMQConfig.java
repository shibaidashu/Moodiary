package com.example.demo.transaction.Config;

import com.example.demo.transaction.Impl.TransactionServiceImpl;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String QUEUE_NAME = "points.queue";
    private static final String EXCHANGE_NAME = "points.exchange";
    private static final String ROUTING_KEY = "points.routingkey";

    @Bean
    public Queue pointsQueue() {
        return new Queue(QUEUE_NAME, true); // 持久化队列
    }

    @Bean
    public Exchange pointsExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue pointsQueue, Exchange pointsExchange) {
        return BindingBuilder.bind(pointsQueue).to(pointsExchange).with(ROUTING_KEY).noargs();
    }

    // 定义 Jackson 的消息转换器
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // 配置 RabbitTemplate 使用 Jackson2JsonMessageConverter
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    // 配置监听器适配器使用 Jackson2JsonMessageConverter
    @Bean
    public MessageListenerAdapter messageListenerAdapter(TransactionServiceImpl transactionService) {
        return new MessageListenerAdapter(transactionService, jackson2JsonMessageConverter());
    }
}

