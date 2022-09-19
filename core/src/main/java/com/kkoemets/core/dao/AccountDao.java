package com.kkoemets.core.dao;

import com.kkoemets.domain.id.AccountId;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountDao {

    AccountId getNextSeqValue();

}
