package ru.test.project.account.balance.service.server.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.test.project.account.balance.service.server.dao.AccountBalanceDao;
import ru.test.project.account.balance.service.server.error.AmountFitLongException;
import ru.test.project.account.balance.service.server.error.ItemNotFoundException;
import ru.test.project.account.balance.service.server.service.AccountBalanceService;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of {@link AccountBalanceService}
 */
@Service
@RequiredArgsConstructor
public class AccountBalanceServiceImpl implements AccountBalanceService {

    private final AccountBalanceDao accountBalanceDao;

    @Override
    @Cacheable(cacheNames = "balance", key = "#id")
    public Long getAmount(Integer id) throws ItemNotFoundException {
        return accountBalanceDao.getAmount(id);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "balance", key = "#id")
    public void addAmount(Integer id, Long value) throws AmountFitLongException {
        accountBalanceDao.addAmount(id, value);
    }
}
