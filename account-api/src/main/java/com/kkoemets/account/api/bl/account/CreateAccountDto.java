package com.kkoemets.account.api.bl.account;

import com.kkoemets.domain.codes.CountryIsoCode2;
import com.kkoemets.domain.codes.Currency;
import com.kkoemets.domain.id.CustomerId;

import java.util.List;

public record CreateAccountDto(CustomerId customerId, List<Currency> currencies, CountryIsoCode2 country) {
}
