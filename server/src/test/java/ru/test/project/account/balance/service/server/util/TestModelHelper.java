package ru.test.project.account.balance.service.server.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.test.project.account.balance.service.server.dto.AmountDto;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service for generate test items
 */
public class TestModelHelper {
    private static final AtomicInteger atomicInteger = new AtomicInteger(1);
    private static final AtomicLong atomicLong = new AtomicLong(1);

    /**
     * Get next Integer
     *
     * @return next Integer
     */
    public static Integer getInt() {
        return atomicInteger.incrementAndGet();
    }

    /**
     * Get next Long
     *
     * @return next Long
     */
    public static Long getLong() {
        return atomicLong.incrementAndGet();
    }

    /**
     * Get rounded time by given round
     *
     * @param instant - time for round
     * @param round   - value for round
     * @return rounded time by given round
     */
    public static Long getLongFromInstantRound(Instant instant, Long round) {
        return (instant.toEpochMilli() / round) * round;
    }

    /**
     * Get object of {@link AmountDto}
     *
     * @return object of {@link AmountDto}
     */
    public static AmountDto getAmountDto() {
        AmountDto amountDto = new AmountDto();
        amountDto.setValue(getLong());
        return amountDto;
    }

    /**
     * Class for errors
     */
    @Data
    @NoArgsConstructor
    public static class AwesomeException {
        private String message;
        private String status;
        private Integer code;
    }
}
