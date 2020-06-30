package ru.test.project.account.balance.service.server.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import ru.test.project.account.balance.service.server.dto.AmountDto;

import lombok.Data;
import lombok.NoArgsConstructor;

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
