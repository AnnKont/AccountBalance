package ru.test.project.account.balance.service.server.error;

import ru.test.project.account.balance.service.server.constant.ExceptionDescription;

/**
 * Exception when id is not valid
 */
public class BadAmountException extends BadRequestException {
    public BadAmountException() {
        super(ExceptionDescription.EXCEPTION_BAD_AMOUNT);
    }
}
