package ru.test.project.account.balance.service.client.service.impl;

import org.springframework.stereotype.Service;

import ru.test.project.account.balance.service.client.service.HttpService;
import ru.test.project.account.balance.service.client.service.StatisticService;
import ru.test.project.account.balance.service.client.service.UrlService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of service {@link StatisticService}
 */
@Slf4j
@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private HttpService<Long, Long> httpService;
    private final UrlService urlService;

    @Override
    public void clearMaps() {
        log.info("Do http delete request for clear statistic");
        httpService.delete(urlService.getStatisticClearUrl());
    }
}
