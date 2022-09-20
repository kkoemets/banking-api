package com.kkoemets.core.dao;

import com.kkoemets.core.service.AddAccountDto;
import com.kkoemets.domain.id.AccountId;
import com.kkoemets.domain.status.AccountStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountDao {

    AccountId getNextSeqValue();

    void add(@Param("dto") AddAccountDto dto, @Param("status") AccountStatus status);

    boolean exists(@Param("accountId") AccountId accountId);

}
