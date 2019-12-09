package ru.test.project.account.balance.service.server.error;

import ru.test.project.account.balance.service.server.constant.ExceptionDescription;

/**
 * Exception when item not found
 */
public class ItemNotFoundException extends ApiException {
    public ItemNotFoundException(Integer id) {
        super(ExceptionDescription.EXCEPTION_ITEM_NOT_FOUND + id);
    }
}
