package ru.test.project.account.balance.service.server.dao;

import ru.test.project.account.balance.service.server.error.AmountFitLongException;
import ru.test.project.account.balance.service.server.error.ItemNotFoundException;

/**
 * Dao for account balance
 */
public interface AccountBalanceDao {
    /**
     * Get amount of account balance by account id
     *
     * @param id - account id
     * @return amount of account balance
     * @throws ItemNotFoundException - if item not found by id
     */
    long getAmount(Integer id) throws ItemNotFoundException;

    /**
     * Add amount to balance for account
     *
     * @param id     - account id
     * @param amount - amount to add
     * @throws AmountFitLongException - if amount is too large when add
     */
    void addAmount(Integer id, Long amount) throws AmountFitLongException;
}
