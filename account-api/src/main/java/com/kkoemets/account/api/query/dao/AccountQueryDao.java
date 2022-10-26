package com.kkoemets.account.api.query.dao;

import com.kkoemets.account.api.mybatis.annotation.AccountApiQueryDao;
import com.kkoemets.domain.id.AccountId;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@AccountApiQueryDao
public interface AccountQueryDao {

    Optional<FindAccountQueryResultDto> findAccount(@Param("accountId") AccountId accountId);

}
