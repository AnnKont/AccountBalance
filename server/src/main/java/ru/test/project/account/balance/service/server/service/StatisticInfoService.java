package ru.test.project.account.balance.service.server.service;

import ru.test.project.account.balance.service.server.models.StatisticInfo;

/**
 * Service for get statistic
 */
public interface StatisticInfoService {
    /**
     * Get map of requests
     *
     * @return map of requests
     */
    StatisticInfo get();

    /**
     * Increment count of requests
     */
    void increment();

    /**
     * Clear counts of requests
     */
    void clear();

    /**
     * Clear counts of requests if no request
     */
    void clearIfNoRequests();
}
