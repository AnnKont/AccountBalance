package ru.test.project.account.balance.service.server.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.test.project.account.balance.service.server.error.BadAmountException;
import ru.test.project.account.balance.service.server.error.BadIdException;
import ru.test.project.account.balance.service.server.service.ValidationService;

/**
 * Implementation of {@link ValidationService}
 */
@Slf4j
@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public void validId(Integer id) throws BadIdException {
        if (id == null || id <= 0) {
            log.error("Invalid id: {}", id);
            throw new BadIdException(id);
        }
    }

    @Override
    public void validAmount(Long amount) throws BadAmountException {
        if (amount == null) {
            log.error("Invalid amount: {}", amount);
            throw new BadAmountException();
        }
    }
}
