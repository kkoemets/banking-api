package com.kkoemets.account.api.bl.account;

import com.kkoemets.account.api.exception.BadRequestException;
import com.kkoemets.core.service.AccountService;
import com.kkoemets.core.service.AddAccountDto;
import com.kkoemets.core.service.AllowedCurrencyService;
import com.kkoemets.core.service.BalanceService;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.CountryIsoCode2;
import com.kkoemets.domain.codes.Currency;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.CustomerId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

import static com.kkoemets.account.api.exception.AccountApiExceptionMessage.ERROR_DISALLOWED_CURRENCIES;
import static com.kkoemets.account.api.exception.AccountApiExceptionMessage.ERROR_DUPLICATE_CURRENCIES;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class CreateAccount {
    private static final Logger log = getLogger(CreateAccount.class);

    @Autowired
    private AccountService accounts;
    @Autowired
    private BalanceService balances;
    @Autowired
    private AllowedCurrencyService allowedCurrencies;

    @Transactional
    public CreateAccountResultDto create(CreateAccountDto dto) {
        CustomerId customerId = dto.customerId();
        List<Currency> currencies = dto.currencies();
        CountryIsoCode2 country = dto.country();
        log.info("Creating account for customer-{} with currencies-{}, country-{}", customerId, currencies, country);

        validate(currencies);

        AccountId accountId = accounts.add(new AddAccountDto(dto.customerId(), dto.country()));
        log.info("Created account-{}", accountId);

        List<Money> createdBalances = balances.insert(accountId, dto.currencies());

        log.info("Account created");
        return new CreateAccountResultDto(accountId, customerId, createdBalances);
    }

    private void validate(List<Currency> currencies) {
        if (hasDuplicates(currencies)) {
            log.warn("Duplicate currencies exist");
            throw new BadRequestException(ERROR_DUPLICATE_CURRENCIES);
        }

        if (containsDisallowedCurrency(currencies)) {
            log.warn("Contains disallowed currency");
            throw new BadRequestException(ERROR_DISALLOWED_CURRENCIES);
        }
    }

    private boolean containsDisallowedCurrency(List<Currency> currencies) {
        return currencies.stream().anyMatch(currency -> !allowedCurrencies.isAllowed(currency));
    }

    private boolean hasDuplicates(List<Currency> currencies) {
        return currencies.size() > 1 && new HashSet<>(currencies).size() != currencies.size();
    }

}
