package com.kkoemets.account.api.query;

import com.kkoemets.account.api.query.dao.FindTransactionQueryResultDto;
import com.kkoemets.account.api.query.dao.TransactionQueryDao;
import com.kkoemets.domain.id.AccountId;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionQuery {

    @Autowired
    private TransactionQueryDao dao;

    public List<FindTransactionQueryResultDto> findTransactions(@Param("accountId") AccountId accountId) {
        return dao.findTransactions(accountId);
    }

}
