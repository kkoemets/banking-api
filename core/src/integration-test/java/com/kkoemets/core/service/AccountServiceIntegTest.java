package com.kkoemets.core.service;

import com.kkoemets.core.CoreIntegTest;
import com.kkoemets.domain.id.CustomerId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static com.kkoemets.domain.codes.CountryIsoCode2.EE;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AccountServiceIntegTest extends CoreIntegTest {

    @Autowired
    private AccountService service = new AccountService();

    @Test
    public void shouldReturnAccountId() {
        assertNotNull(service.getNextSeqValue());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void shouldExecute() {
        service.add(new AddAccountDto(service.getNextSeqValue(), new CustomerId(1L), EE));
    }

}
