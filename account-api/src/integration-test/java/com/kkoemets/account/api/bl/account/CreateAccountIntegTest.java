package com.kkoemets.account.api.bl.account;

import com.kkoemets.account.api.AccountApiIntegTest;
import com.kkoemets.account.api.exception.BadRequestException;
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

import static com.kkoemets.account.api.exception.AccountApiExceptionMessage.ERROR_DISALLOWED_CURRENCIES;
import static com.kkoemets.account.api.exception.AccountApiExceptionMessage.ERROR_DUPLICATE_CURRENCIES;
import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static com.kkoemets.domain.codes.Currency.EUR;
import static com.kkoemets.domain.codes.Currency.USD;
import static java.math.BigDecimal.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

public class CreateAccountIntegTest extends AccountApiIntegTest {

    @Autowired
    private CreateAccount createAccount;
    @Autowired
    private AccountService accounts;
    @Autowired
    private BalanceService balances;

    @Test
    public void createsAccount() {
        CreateAccountResultDto resultDto = createAccount.create(createDto());
        assertTrue(accounts.exists(resultDto.accountId()));
    }

    @Test
    public void failsIfDisallowedCurrency() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> createAccount.create(createDto(List.of(EUR, Currency.create("RUB")))));

        assertThat(exception.getMessage(), is(ERROR_DISALLOWED_CURRENCIES));
    }

    @Test
    public void failsIfDuplicateCurrenciesAreInDto() {
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> createAccount.create(createDto(List.of(EUR, USD, EUR))));

        assertThat(exception.getMessage(), is(ERROR_DUPLICATE_CURRENCIES));
    }

    @Test
    public void balanceIsCreated() {
        CreateAccountDto dto = createDto();
        assertThat(dto.currencies().size(), is(1));

        CreateAccountResultDto resultDto = createAccount.create(dto);
        assertTrue(findBalance(resultDto.accountId(), getCurrency(dto)).isPresent());
    }

    @Test
    public void balancesAreCreated() {
        List<Currency> currencies = List.of(EUR, USD);
        CreateAccountDto dto = createDto(currencies);
        CreateAccountResultDto resultDto = createAccount.create(dto);
        assertTrue(currencies
                .stream()
                .allMatch(currency -> balances.findBalance(resultDto.accountId(), currency).isPresent()));
    }

    @Test
    public void initialBalanceIsZero() {
        CreateAccountDto dto = createDto();
        CreateAccountResultDto resultDto = createAccount.create(dto);
        assertEquals(0, findBalance(resultDto.accountId(), getCurrency(dto))
                .orElseThrow()
                .amount()
                .compareTo(ZERO));
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
        return new CreateAccountDto(new CustomerId(1L), currencies, EE);
    }

}
