package com.kkoemets.core.service;

import com.kkoemets.core.dao.AccountDao;
import com.kkoemets.domain.id.AccountId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.kkoemets.domain.status.AccountStatus.ACTIVE;

@Service
public class AccountService {

    @Autowired
    private AccountDao dao;

    public AccountId getNextSeqValue() {
        return dao.getNextSeqValue();
    }

    public void add(AddAccountDto dto) {
        dao.add(dto, ACTIVE);
    }

    public boolean exists(AccountId accountId) {
        return dao.exists(accountId);
    }

    public void lock(AccountId accountId) {
        dao.lock(accountId);
    }

}
