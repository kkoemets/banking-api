package com.kkoemets.account.api.bl.account;

import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.CustomerId;

import java.util.List;

public record CreateAccountResultDto(AccountId accountId, CustomerId customerId, List<Money> balances) {
}
