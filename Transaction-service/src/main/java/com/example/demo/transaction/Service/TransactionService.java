package com.example.demo.transaction.Service;

public interface TransactionService {
    void createTransaction(Integer userId, Integer changeAmount, String transactionType, String description);
}
