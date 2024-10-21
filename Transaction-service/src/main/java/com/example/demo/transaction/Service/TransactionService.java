package com.example.demo.transaction.Service;

import com.example.demo.common.Entity.PageBean;

public interface TransactionService {

    void createTransaction(Integer userId, Integer changeAmount, String transactionType, String description);

    PageBean page(int userId, Integer page, Integer pageSize);

}

