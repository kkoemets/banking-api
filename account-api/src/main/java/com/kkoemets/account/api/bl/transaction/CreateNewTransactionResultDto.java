package com.kkoemets.account.api.bl.transaction;

import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.TransactionId;

public record CreateNewTransactionResultDto(TransactionId transactionId, AccountId accountId, Money newBalance) {
}
