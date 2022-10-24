package com.kkoemets.core.service;

import com.kkoemets.domain.codes.CountryIsoCode2;
import com.kkoemets.domain.id.CustomerId;

public record AddAccountDto(CustomerId customerId, CountryIsoCode2 country) {
}
