package com.kkoemets.core.service;

import com.kkoemets.core.CoreIntegTest;
import com.kkoemets.domain.id.CustomerId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static com.kkoemets.domain.codes.CountryIsoCode2.EE;


public class AccountServiceIntegTest extends CoreIntegTest {

    @Autowired
    private AccountService service = new AccountService();

    @Test
    @Transactional
    @Rollback(value = false)
    public void shouldExecute() {
        service.add(new AddAccountDto(new CustomerId(1L), EE));
    }

}
