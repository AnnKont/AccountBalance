package ru.test.project.account.balance.service.server.error;

/**
 * General Exception for not valid data
 */
public class BadRequestException extends ApiException {
    public BadRequestException(String msg) {
        super(msg);
    }
}
