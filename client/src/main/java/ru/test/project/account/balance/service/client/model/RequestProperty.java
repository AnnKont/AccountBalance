package ru.test.project.account.balance.service.client.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Property for requests
 */
@Data
@NoArgsConstructor
public class RequestProperty {
    /**
     * Count of request getAmount
     */
    private int rCount;

    /**
     * Count of request addAmount
     */
    private int wCount;

    /**
     * Identificators
     */
    private List<Integer> ids;

    /**
     * Flag of clear statistic after request
     */
    private boolean clearStatisticAfterRequest;
}
