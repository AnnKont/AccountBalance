package ru.test.project.account.balance.service.server.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ru.test.project.account.balance.service.server.models.StatisticInfo;
import ru.test.project.account.balance.service.server.service.StatisticInfoService;
import ru.test.project.account.balance.service.server.service.StatisticService;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of {@link StatisticService}
 */
@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    @Qualifier("addRequestStatisticMapServiceImpl")
    private final StatisticInfoService addRequestStatisticInfoService;
    @Qualifier("getRequestStatisticMapServiceImpl")
    private final StatisticInfoService getRequestStatisticInfoService;

    @Override
    public StatisticInfo getStatisticForGetRequest() {
        return getRequestStatisticInfoService.get();
    }

    @Override
    public StatisticInfo getStatisticForAddRequest() {
        return addRequestStatisticInfoService.get();
    }

    @Override
    public void clear() {
        getRequestStatisticInfoService.clear();
        addRequestStatisticInfoService.clear();
    }

    @Override
    public void clearIfNoRequests() {
        getRequestStatisticInfoService.clearIfNoRequests();
        addRequestStatisticInfoService.clearIfNoRequests();
    }
}
