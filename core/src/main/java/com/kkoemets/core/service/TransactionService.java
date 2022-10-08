package com.kkoemets.core.service;

import com.kkoemets.core.dao.TransactionDao;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.TransactionDirection;
import com.kkoemets.domain.id.TransactionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.kkoemets.domain.codes.TransactionStatus.NEW;

@Service
public class TransactionService {

    @Autowired
    private TransactionDao dao;

    public TransactionId getNextSeqValue() {
        return dao.getNextSeqValue();
    }

    public void add(AddTransactionDto dto) {
        dao.add(dto, NEW);
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

    public boolean isInNewStatus(TransactionId transactionId) {
        return dao.findStatus(transactionId) == NEW;
    }

    public String findDescription(TransactionId transactionId) {
        return dao.findDescription(transactionId);
    }

}
