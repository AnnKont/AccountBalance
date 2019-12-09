package ru.test.project.account.balance.service.server.service;

import ru.test.project.account.balance.service.server.error.BadAmountException;
import ru.test.project.account.balance.service.server.error.BadIdException;

/**
 * Service for validation input data
 */
public interface ValidationService {

    /**
     * Validate account id
     *
     * @param id - account id
     * @throws BadIdException - account id not valid
     */
    void validId(Integer id) throws BadIdException;

    /**
     * Validate amount
     *
     * @param amount - amount
     * @throws BadAmountException - amount is not valid
     */
    void validAmount(Long amount) throws BadAmountException;
}
