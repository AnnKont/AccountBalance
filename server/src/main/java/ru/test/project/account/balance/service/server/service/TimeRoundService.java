package ru.test.project.account.balance.service.server.service;

import java.time.Instant;

/**
 * Service for round number
 */
public interface TimeRoundService {
    /**
     * Get rounded second from given time
     *
     * @param timestamp - given time
     * @return rounded second
     */
    long roundToSecond(Instant timestamp);
}
