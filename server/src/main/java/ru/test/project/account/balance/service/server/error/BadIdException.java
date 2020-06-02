package ru.test.project.account.balance.service.server.error;

import ru.test.project.account.balance.service.server.constant.ExceptionDescription;

/**
 * Exception when id is not valid
 */
public class BadIdException extends BadRequestException {
    public BadIdException(Integer id) {
        super(ExceptionDescription.EXCEPTION_BAD_ID + id);
    }
}
