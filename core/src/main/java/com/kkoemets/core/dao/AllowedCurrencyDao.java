package com.kkoemets.core.dao;

import com.kkoemets.core.mybatis.annotation.CoreDao;
import com.kkoemets.domain.codes.Currency;

import java.util.Set;

@CoreDao
public interface AllowedCurrencyDao {

    Set<Currency> findAll();

}
