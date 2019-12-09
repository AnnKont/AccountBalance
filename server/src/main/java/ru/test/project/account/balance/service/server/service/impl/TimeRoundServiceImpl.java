package ru.test.project.account.balance.service.server.service.impl;

import org.springframework.stereotype.Service;
import ru.test.project.account.balance.service.server.service.TimeRoundService;

import java.time.Instant;

/**
 * Implementation of {@link TimeRoundService}
 */
@Service
public class TimeRoundServiceImpl implements TimeRoundService {

    /**
     * Count of millis in 1 second
     */
    private static final Long SECOND_MILLIS = 1000L;

    @Override
    public long roundToSecond(Instant timestamp) {
        return (timestamp.toEpochMilli() / SECOND_MILLIS) * SECOND_MILLIS;
    }
}
