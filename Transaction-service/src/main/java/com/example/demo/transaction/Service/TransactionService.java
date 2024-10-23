package com.example.demo.transaction.Service;

import com.example.demo.common.Entity.PageBean;

public interface TransactionService {

    PageBean page(int userId, Integer page, Integer pageSize);

}

