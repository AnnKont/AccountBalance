package ru.test.project.account.balance.service.server.constant;

/**
 * Description of exception
 */
public interface ExceptionDescription {

    /**
     * Description of item not found exception
     */
    String EXCEPTION_ITEM_NOT_FOUND = "Item is not found by id: ";

    /**
     * Description of invalid id
     */
    String EXCEPTION_BAD_ID = "Identificator is not valid: ";

    /**
     * Description of invalid amount
     */
    String EXCEPTION_BAD_AMOUNT = "Amount is not valid";

    /**
     * Description of amount after add is not fit in long
     */
    String EXCEPTION_AMOUNT_NOT_FIT_LONG = "Amount after add is not fit in long: ";
}
