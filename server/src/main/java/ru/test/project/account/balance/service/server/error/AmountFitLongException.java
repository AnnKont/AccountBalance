package ru.test.project.account.balance.service.server.error;

import ru.test.project.account.balance.service.server.constant.ExceptionDescription;

import java.math.BigInteger;

/**
 * Exception when amount after add not fit in long
 */
public class AmountFitLongException extends ApiException {
    public AmountFitLongException(BigInteger value) {
        super(ExceptionDescription.EXCEPTION_AMOUNT_NOT_FIT_LONG + String.valueOf(value));
    }
}
