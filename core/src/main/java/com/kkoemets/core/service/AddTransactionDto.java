package com.kkoemets.core.service;

import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.TransactionDirection;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.TransactionId;

public record AddTransactionDto(TransactionId transactionId, AccountId accountId, Money amount, String description,
                                TransactionDirection direction) {
}
