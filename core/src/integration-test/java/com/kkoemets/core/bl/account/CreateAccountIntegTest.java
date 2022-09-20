package com.kkoemets.core.bl.account;

import com.kkoemets.core.CoreIntegTest;
import com.kkoemets.core.service.AccountService;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.id.CustomerId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static java.util.Collections.emptySet;

public class CreateAccountIntegTest extends CoreIntegTest {

    @Autowired
    private CreateAccount createAccount;
    @Autowired
    private AccountService accounts;

    @Test
    public void createsAccount() {
        createAccount.create(new CreateAccountDto(accounts.getNextSeqValue(), new CustomerId(1L), emptySet(), EE));
    }

    @Test
    public void successIfAlreadyExists() {
        AccountId nextSeqValue = accounts.getNextSeqValue();
        createAccount.create(new CreateAccountDto(nextSeqValue, new CustomerId(1L), emptySet(), EE));
        createAccount.create(new CreateAccountDto(nextSeqValue, new CustomerId(1L), emptySet(), EE));
    }

}
