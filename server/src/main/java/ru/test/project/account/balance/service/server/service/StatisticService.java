package ru.test.project.account.balance.service.server.service;

import ru.test.project.account.balance.service.server.models.StatisticInfo;

/**
 * Service for get statistic
 */
public interface StatisticService {

    /**
     * Get map of requests for get amount by second
     *
     * @return map of requests for get amount by second
     */
    StatisticInfo getStatisticForGetRequest();

    /**
     * Get map of requests for add amount by second
     *
     * @return map of requests for add amount by second
     */
    StatisticInfo getStatisticForAddRequest();

    /**
     * Clear all statistic
     */
    void clear();

    /**
     * Clear statistic if no request
     */
    void clearIfNoRequests();
}
