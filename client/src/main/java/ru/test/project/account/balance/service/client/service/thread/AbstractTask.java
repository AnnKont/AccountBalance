package ru.test.project.account.balance.service.client.service.thread;

import lombok.extern.slf4j.Slf4j;
import ru.test.project.account.balance.service.client.service.AccountService;

import java.util.List;

/**
 * General task to work with balance(get/add)
 */
@Slf4j
public abstract class AbstractTask extends ThreadTask {

    protected final AccountService accountService;

    protected final List<Integer> ids;

    public AbstractTask(AccountService accountService, List<Integer> ids) {
        super();
        this.accountService = accountService;
        this.ids = ids;
    }
}
