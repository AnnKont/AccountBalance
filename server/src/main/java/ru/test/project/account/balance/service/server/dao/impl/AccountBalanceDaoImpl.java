package ru.test.project.account.balance.service.server.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.test.project.account.balance.service.server.dao.AccountBalanceDao;
import ru.test.project.account.balance.service.server.error.AmountFitLongException;
import ru.test.project.account.balance.service.server.error.ItemNotFoundException;

import java.math.BigInteger;

@Slf4j
@Service
public class AccountBalanceDaoImpl implements AccountBalanceDao {

    /**
     * SQL request for select account balance by id
     */
    private final String SQL_GET_BY_ID = "SELECT amount FROM account_balance where id = ?";
    /**
     * SQL request for update account balance by id
     */
    private final String SQL_UPDATE_BALANCE = "UPDATE account_balance SET amount = amount + ? WHERE id = ?";
    /**
     * SQL request for create account balance by id
     */
    private final String SQL_INSERT_BALANCE = "INSERT INTO account_balance (id, amount) VALUES (?, ?)";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public long getAmount(Integer id) throws ItemNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[]{id}, Long.class);
        } catch (EmptyResultDataAccessException e) {
            log.error("Account balance not found by id: {}", id);
            throw new ItemNotFoundException(id);
        }
    }

    @Override
    public void addAmount(Integer id, Long amount) throws AmountFitLongException {
        try {
            if (jdbcTemplate.update(SQL_UPDATE_BALANCE, amount, id) == 0) {
                jdbcTemplate.update(SQL_INSERT_BALANCE, id, amount);
            }
        } catch (DataIntegrityViolationException e) {
            log.error("Amount is too big after operation add: {}", e.getMessage());
            try {
                throw new AmountFitLongException(BigInteger.valueOf(amount).add(BigInteger.valueOf(getAmount(id))));
            } catch (ItemNotFoundException ex) {
                log.error("Account balance not found by id: {}", id);
            }
        }
    }
}
