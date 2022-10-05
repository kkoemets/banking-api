package com.kkoemets.core.bl.account;

import com.kkoemets.core.CoreIntegTest;
import com.kkoemets.core.service.AccountService;
import com.kkoemets.core.service.BalanceService;
import com.kkoemets.domain.balance.Money;
import com.kkoemets.domain.codes.Currency;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.CustomerId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static com.kkoemets.domain.codes.Currency.EUR;
import static com.kkoemets.domain.codes.Currency.USD;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateAccountIntegTest extends CoreIntegTest {

    @Autowired
    private CreateAccount createAccount;
    @Autowired
    private AccountService accounts;
    @Autowired
    private BalanceService balances;

    @Test
    public void createsAccount() {
        CreateAccountDto dto = createDto();
        AccountId accountId = dto.accountId();
        assertFalse(accounts.exists(accountId));
        createAccount.create(dto);
        assertTrue(accounts.exists(accountId));
    }

    @Test
    public void successIfAlreadyExists() {
        CreateAccountDto dto = createDto();
        createAccount.create(dto);
        createAccount.create(dto);
    }

    @Test
    public void balanceIsCreated() {
        CreateAccountDto dto = createDto();
        AccountId accountId = dto.accountId();
        Currency currency = getCurrency(dto);
        assertTrue(findBalance(accountId, currency).isEmpty());
        createAccount.create(dto);
        assertTrue(findBalance(accountId, currency).isPresent());
    }

    @Test
    public void balancesAreCreated() {
        List<Currency> currencies = List.of(EUR, USD);
        CreateAccountDto dto = createDto(currencies);
        AccountId accountId = dto.accountId();
        assertTrue(currencies.stream().noneMatch(currency -> balances.findBalance(accountId, currency).isPresent()));
        createAccount.create(dto);
        assertTrue(currencies.stream().allMatch(currency -> balances.findBalance(accountId, currency).isPresent()));
    }

    @Test
    public void initialBalanceIsZero() {
        CreateAccountDto dto = createDto();
        AccountId accountId = dto.accountId();
        assertTrue(findBalance(accountId, getCurrency(dto)).isEmpty());
        createAccount.create(dto);
        assertTrue(findBalance(accountId, getCurrency(dto)).isPresent());
    }

    private Optional<Money> findBalance(AccountId accountId, Currency currency) {
        return balances.findBalance(accountId, currency);
    }

    private Currency getCurrency(CreateAccountDto dto) {
        return dto.currencies().stream().findAny().orElseThrow();
    }

    private CreateAccountDto createDto() {
        return createDto(List.of(EUR));
    }

    private CreateAccountDto createDto(List<Currency> currencies) {
        return new CreateAccountDto(accounts.getNextSeqValue(), new CustomerId(1L), currencies, EE);
    }

}
