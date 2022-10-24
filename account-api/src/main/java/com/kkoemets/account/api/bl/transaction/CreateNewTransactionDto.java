package com.kkoemets.account.api.bl.transaction;

import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.TransactionDirection;
import com.kkoemets.domain.id.AccountId;

public record CreateNewTransactionDto(AccountId accountId, Money amount, String description,
                                      TransactionDirection direction) {
}
