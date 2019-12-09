package ru.test.project.account.balance.service.server.error;

/**
 * General Exception Api
 */
public class ApiException extends Exception {
    public ApiException(String msg) {
        super(msg);
    }
}
