package com.kkoemets.core.service;

import com.kkoemets.core.dao.AllowedCurrencyDao;
import com.kkoemets.domain.codes.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class AllowedCurrencyService {

    @Autowired
    private AllowedCurrencyDao dao;

    @CachePut(value = "allowedCurrencies")
    public boolean isAllowed(Currency currency) {
        return dao.findAll().contains(currency);
    }

}
