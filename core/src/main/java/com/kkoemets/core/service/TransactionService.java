package com.kkoemets.core.service;

import com.kkoemets.core.dao.TransactionDao;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.TransactionDirection;
import com.kkoemets.domain.id.TransactionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionDao dao;

    public TransactionId add(AddTransactionDto dto) {
        return dao.add(dto);
    }

    public boolean exists(TransactionId transactionId) {
        return dao.exists(transactionId);
    }

    public Money findAmount(TransactionId transactionId) {
        return dao.findAmount(transactionId);
    }

    public TransactionDirection findDirection(TransactionId transactionId) {
        return dao.findDirection(transactionId);
    }

    public String findDescription(TransactionId transactionId) {
        return dao.findDescription(transactionId);
    }

}
