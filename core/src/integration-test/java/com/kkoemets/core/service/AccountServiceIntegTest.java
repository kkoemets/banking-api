package com.kkoemets.core.service;

import com.kkoemets.core.CoreIntegTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class AccountServiceIntegTest extends CoreIntegTest {

    @Autowired
    private AccountService service = new AccountService();

    @Test
    public void shouldExecute() {
        service.add();
    }

}
