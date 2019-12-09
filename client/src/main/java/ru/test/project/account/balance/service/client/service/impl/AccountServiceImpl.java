package ru.test.project.account.balance.service.client.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.project.account.balance.service.client.dto.AmountDto;
import ru.test.project.account.balance.service.client.service.AccountService;
import ru.test.project.account.balance.service.client.service.HttpService;
import ru.test.project.account.balance.service.client.service.UrlService;

/**
 * Implementation of service {@link AccountService}
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private HttpService<Long, AmountDto> httpService;

    @Autowired
    private UrlService urlService;

    @Override
    public Long getAmount(Integer id) {
        log.info("Do http get request for get amount by id: {}", id);
        return httpService.get(urlService.getBalanceUrl(id), Long.class);
    }

    @Override
    public void addAmount(Integer id, Long value) {
        log.info("Do http put request for add amount by id: {}, amount: {}", id, value);
        httpService.put(urlService.getBalanceUrl(id), new AmountDto(value));
    }
}
