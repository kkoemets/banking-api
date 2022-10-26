package com.kkoemets.account.api.query;

import com.kkoemets.account.api.query.dao.BalanceQueryDao;
import com.kkoemets.account.api.query.dao.FindBalanceQueryResultDto;
import com.kkoemets.domain.id.AccountId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceQuery {

    @Autowired
    private BalanceQueryDao dao;

    public List<FindBalanceQueryResultDto> findBalances(AccountId accountId) {
        return dao.findBalances(accountId);
    }

}
