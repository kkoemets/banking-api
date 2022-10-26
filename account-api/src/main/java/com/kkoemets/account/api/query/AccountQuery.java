package com.kkoemets.account.api.query;

import com.kkoemets.account.api.query.dao.AccountQueryDao;
import com.kkoemets.account.api.query.dao.FindAccountQueryResultDto;
import com.kkoemets.domain.id.AccountId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountQuery {

    @Autowired
    private AccountQueryDao dao;

    public Optional<FindAccountQueryResultDto> find(AccountId accountId) {
        return dao.findAccount(accountId);
    }

}
