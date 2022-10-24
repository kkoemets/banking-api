package com.kkoemets.core.service;

import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.TransactionDirection;
import com.kkoemets.domain.id.AccountId;

public record AddTransactionDto(AccountId accountId, Money amount, String description,
                                TransactionDirection direction) {
}
