package ru.test.project.account.balance.service.server.service;

import java.time.Instant;
import java.util.Map;

/**
 * Service for get statistic
 */
public interface StatisticService {

    /**
     * Get map of requests for get amount by second
     *
     * @return map of requests for get amount by second
     */
    Map<Instant, Long> getCountOfGetAmountBySecond();

    /**
     * Get all count of request to get amount
     *
     * @return count of request
     */
    Long getAllCountOfGetAmount();

    /**
     * Get map of requests for add amount by second
     *
     * @return map of requests for add amount by second
     */
    Map<Instant, Long> getCountOfAddAmountBySecond();

    /**
     * Get all count of request to add amount
     *
     * @return count of request
     */
    Long getAllCountOfAddAmount();

    /**
     * Increment count of get amount requests
     */
    void incrementGetAmountCount();

    /**
     * Increment count of add amount requests
     */
    void incrementAddAmountCount();

    /**
     * Clear all statistic
     */
    void clearMaps();
}
