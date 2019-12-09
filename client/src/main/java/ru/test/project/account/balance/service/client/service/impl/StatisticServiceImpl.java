package ru.test.project.account.balance.service.client.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.project.account.balance.service.client.service.HttpService;
import ru.test.project.account.balance.service.client.service.StatisticService;
import ru.test.project.account.balance.service.client.service.UrlService;

/**
 * Implementation of service {@link StatisticService}
 */
@Slf4j
@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private HttpService<Long, Long> httpService;

    @Autowired
    private UrlService urlService;

    @Override
    public void clearMaps() {
        log.info("Do http delete request for clear statistic");
        httpService.delete(urlService.getStatisticClearUrl());
    }
}
