package ru.test.project.account.balance.service.server.service.impl;

import java.time.Instant;

import org.springframework.stereotype.Service;

import ru.test.project.account.balance.service.server.service.TimeRoundService;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of {@link TimeRoundService}
 */
@Service
@RequiredArgsConstructor
public class TimeRoundServiceImpl implements TimeRoundService {

    /**
     * Count of millis in 1 second
     */
    private static final long SECOND_MILLIS = 1000;

    @Override
    public long roundToSecond(Instant timestamp) {
        return (timestamp.toEpochMilli() / SECOND_MILLIS) * SECOND_MILLIS;
    }
}
