package ru.test.project.account.balance.service.server.error;

import java.math.BigInteger;

import ru.test.project.account.balance.service.server.constant.ExceptionDescription;

/**
 * Exception when amount after add not fit in long
 */
public class AmountFitLongException extends ApiException {
    public AmountFitLongException(BigInteger value) {
        super(ExceptionDescription.EXCEPTION_AMOUNT_NOT_FIT_LONG + value);
    }
}
