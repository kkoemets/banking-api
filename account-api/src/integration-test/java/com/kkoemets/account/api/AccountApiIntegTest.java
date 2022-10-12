package com.kkoemets.account.api;

import com.kkoemets.core.bl.account.CreateAccount;
import com.kkoemets.core.bl.account.CreateAccountDto;
import com.kkoemets.core.service.AccountService;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.CustomerId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static com.kkoemets.domain.codes.Currency.EUR;

@SpringBootTest
public abstract class AccountApiIntegTest {

    @Autowired
    private AccountService accounts;
    @Autowired
    private CreateAccount createAccount;

    @BeforeEach
    public void beforeEach(TestInfo info) {
        MDC.put("requestId", "%s.%s"
                .formatted(Arrays.stream(this.getClass().getName().split("\\."))
                                .reduce((a, b) -> b)
                                .stream()
                                .findFirst()
                                .orElseThrow(),
                        info.getTestMethod()
                                .orElseThrow()
                                .getName()));
    }

    protected AccountId createAccount() {
        AccountId accountId = accounts.getNextSeqValue();
        createAccount.create(new CreateAccountDto(accountId, new CustomerId(1L), List.of(EUR), EE));
        return accountId;
    }

}
