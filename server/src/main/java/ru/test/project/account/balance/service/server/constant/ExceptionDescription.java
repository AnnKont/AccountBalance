package ru.test.project.account.balance.service.server.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Description of exception
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionDescription {

    /**
     * Description of item not found exception
     */
    public static final String EXCEPTION_ITEM_NOT_FOUND = "Item is not found by id: ";

    /**
     * Description of invalid id
     */
    public static final String EXCEPTION_BAD_ID = "Identificator is not valid: ";

    /**
     * Description of invalid amount
     */
    public static final String EXCEPTION_BAD_AMOUNT = "Amount is not valid";

    /**
     * Description of amount after add is not fit in long
     */
    public static final String EXCEPTION_AMOUNT_NOT_FIT_LONG = "Amount after add is not fit in long: ";
}
