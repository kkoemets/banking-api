package com.kkoemets.core.bl.account;

import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.codes.CountryIsoCode2;
import com.kkoemets.domain.codes.Currency;
import com.kkoemets.domain.id.CustomerId;

import java.util.Set;

public record CreateAccountDto(AccountId accountId, CustomerId customerId,
                               Set<Currency> currencies, CountryIsoCode2 country) {
}
