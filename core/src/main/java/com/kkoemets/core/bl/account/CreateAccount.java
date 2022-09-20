package com.kkoemets.core.bl.account;

import com.kkoemets.core.service.AccountService;
import com.kkoemets.core.service.AddAccountDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class CreateAccount {
    private static final Logger log = getLogger(CreateAccount.class);

    @Autowired
    private AccountService accounts;

    public void create(CreateAccountDto dto) {
        if (accounts.exists(dto.accountId())) {
            log.info("Account already exists");
            return;
        }

        accounts.add(new AddAccountDto(dto.accountId(), dto.customerId(), dto.country()));
    }

}
