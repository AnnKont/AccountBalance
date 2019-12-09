package ru.test.project.account.balance.service.server.service;

import java.util.Map;

/**
 * Service for get statistic
 */
public interface StatisticMapService {
    /**
     * Get map of requests for get amount
     *
     * @return map of requests for get amount
     */
    Map<Long, Long> getMapOfGetAmount();

    /**
     * Get map of requests for add amount
     *
     * @return map of requests for add amount
     */
    Map<Long, Long> getMapOfAddAmount();

    /**
     * Increment count of get amount requests
     */
    void incrementGetAmountCount();

    /**
     * Increment count of add amount requests
     */
    void incrementAddAmountCount();

    /**
     * Clear count of get and add amount requests
     */
    void clearMaps();
}
