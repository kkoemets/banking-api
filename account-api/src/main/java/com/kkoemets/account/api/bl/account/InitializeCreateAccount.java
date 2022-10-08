package com.kkoemets.account.api.bl.account;

import com.kkoemets.account.api.exception.BadRequestException;
import com.kkoemets.core.amqp.message.CreateAccountMessage;
import com.kkoemets.core.amqp.queue.CreateAccountQueue;
import com.kkoemets.core.service.AccountService;
import com.kkoemets.core.service.AllowedCurrencyService;
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

import static com.kkoemets.account.api.exception.ExceptionMessage.ERROR_DISALLOWED_CURRENCIES;
import static com.kkoemets.account.api.exception.ExceptionMessage.ERROR_DUPLICATE_CURRENCIES;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class InitializeCreateAccount {
    private static final Logger log = getLogger(InitializeCreateAccount.class);

    @Autowired
    private AccountService accounts;
    @Autowired
    private CreateAccountQueue createAccountQueue;
    @Autowired
    private AllowedCurrencyService allowedCurrencies;

    @Transactional
    public InitializeCreateAccountResult initialize(InitializeCreateAccountDto dto) {
        CustomerId customerId = dto.customerId();
        List<Currency> currencies = dto.currencies();
        CountryIsoCode2 country = dto.country();
        log.info("Creating account for customer-{} with currencies-{}, country-{}", customerId, currencies, country);

        validate(currencies);

        AccountId accountId = accounts.getNextSeqValue();

        createAccountQueue.send(new CreateAccountMessage(accountId, customerId, currencies, country));

        return new InitializeCreateAccountResult(accountId, customerId,
                currencies
                        .stream()
                        .map(Money::zero)
                        .collect(toList()));
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

    void setCreateAccountQueue(CreateAccountQueue createAccountQueue) {
        this.createAccountQueue = createAccountQueue;
    }

}
