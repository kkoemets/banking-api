package com.kkoemets.core.service;

import com.kkoemets.core.dao.TransactionDao;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.TransactionDirection;
import com.kkoemets.domain.codes.TransactionStatus;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.TransactionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.kkoemets.domain.codes.TransactionDirection.OUT;
import static com.kkoemets.domain.codes.TransactionStatus.*;

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

    public boolean isOutgoing(TransactionId transactionId) {
        return findDirection(transactionId) == OUT;
    }

    public boolean isInNewStatus(TransactionId transactionId) {
        return isStatus(transactionId, NEW);
    }

    public String findDescription(TransactionId transactionId) {
        return dao.findDescription(transactionId);
    }

    public boolean isInProcessedStatus(TransactionId transactionId) {
        return isStatus(transactionId, PROCESSED);
    }

    public boolean isInRejectedStatus(TransactionId transactionId) {
        return isStatus(transactionId, REJECTED);
    }

    public AccountId findAccountId(TransactionId transactionId) {
        return dao.findAccountId(transactionId);
    }

    private boolean isStatus(TransactionId transactionId, TransactionStatus status) {
        return dao.findStatus(transactionId) == status;
    }

    public void setStatus(TransactionId transactionId, TransactionStatus status) {
        if (dao.setStatus(transactionId, status) < 1) {
            throw new FailedUpdateException();
        }
    }

}
