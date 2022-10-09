package com.kkoemets.core.bl.transaction;

import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.TransactionDirection;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.TransactionId;

public record CreateNewTransactionDto(TransactionId transactionId, AccountId accountId, Money amount,
                                      String description, TransactionDirection direction) {
}
