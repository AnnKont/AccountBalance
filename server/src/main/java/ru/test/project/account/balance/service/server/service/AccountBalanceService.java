package ru.test.project.account.balance.service.server.service;

import ru.test.project.account.balance.service.server.error.AmountFitLongException;
import ru.test.project.account.balance.service.server.error.ItemNotFoundException;

/**
 * Service for working with account balance
 */
public interface AccountBalanceService {
    /**
     * Retrieves current balance or zero if addAmount() method was not called before for specified id
     *
     * @param id balance identifier
     * @throws ItemNotFoundException - if id not found in db
     */
    Long getAmount(Integer id) throws ItemNotFoundException;

    /**
     * Increases balance or set if addAmount() method was called first time
     *
     * @param id    balance identifier
     * @param value positive or negative value, which must be added to current balance
     */
    void addAmount(Integer id, Long value) throws AmountFitLongException;
}
